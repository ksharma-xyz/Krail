package xyz.ksharma.krail.trip.planner.ui.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import xyz.ksharma.krail.core.datetime.DateTimeHelper.calculateTimeDifferenceFromNow
import xyz.ksharma.krail.core.datetime.DateTimeHelper.isBefore
import xyz.ksharma.krail.core.datetime.DateTimeHelper.isFuture
import xyz.ksharma.krail.core.datetime.DateTimeHelper.toGenericFormattedTimeString
import xyz.ksharma.krail.core.datetime.DateTimeHelper.toHHMM
import xyz.ksharma.krail.core.datetime.DateTimeHelper.utcToLocalDateTimeAEST
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.trip.planner.network.api.model.TripResponse
import xyz.ksharma.krail.trip.planner.network.api.ratelimit.RateLimiter
import xyz.ksharma.krail.trip.planner.network.api.service.DepArr
import xyz.ksharma.krail.trip.planner.network.api.service.TripPlanningService
import xyz.ksharma.krail.trip.planner.ui.state.alerts.ServiceAlert
import xyz.ksharma.krail.trip.planner.ui.state.datetimeselector.DateTimeSelectionItem
import xyz.ksharma.krail.trip.planner.ui.state.datetimeselector.JourneyTimeOptions
import xyz.ksharma.krail.trip.planner.ui.state.timetable.TimeTableState
import xyz.ksharma.krail.trip.planner.ui.state.timetable.TimeTableUiEvent
import xyz.ksharma.krail.trip.planner.ui.state.timetable.Trip
import xyz.ksharma.krail.trip.planner.ui.timetable.business.buildJourneyList
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class TimeTableViewModel(
    private val tripPlanningService: TripPlanningService,
    private val rateLimiter: RateLimiter,
    private val sandook: Sandook,
) : ViewModel() {

    private val _uiState: MutableStateFlow<TimeTableState> = MutableStateFlow(TimeTableState())
    val uiState: StateFlow<TimeTableState> = _uiState

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
        // Will start fetching the trip as soon as the screen is visible, which means if android-app goes
        // to background and come back up again, the API call will be made.
        // Probably good to have data up to date.
        .onStart {
            println("onStart: Fetching Trip")
            fetchTrip()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(ANR_TIMEOUT), true)

    private val _isActive: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isActive: StateFlow<Boolean> = _isActive.onStart {
        while (true) {
            if (_uiState.value.journeyList.isEmpty().not()) {
                updateTimeText()
            }
            delay(REFRESH_TIME_TEXT_DURATION)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STOP_TIME_TEXT_UPDATES_THRESHOLD.inWholeMilliseconds),
        initialValue = true,
    )

    private val _autoRefreshTimeTable: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val autoRefreshTimeTable: StateFlow<Boolean> = _autoRefreshTimeTable.onStart {
        while (true) {
            if (_uiState.value.journeyList.isEmpty().not() &&
                dateTimeSelectionItem?.date.isFuture().not()
            ) {
                rateLimiter.triggerEvent()
            }
            delay(AUTO_REFRESH_TIME_TABLE_DURATION)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STOP_TIME_TEXT_UPDATES_THRESHOLD.inWholeMilliseconds),
        initialValue = true,
    )

    private val _expandedJourneyId: MutableStateFlow<String?> = MutableStateFlow(null)
    val expandedJourneyId: StateFlow<String?> = _expandedJourneyId

    private var tripInfo: Trip? = null
    private var dateTimeSelectionItem: DateTimeSelectionItem? = null

    private var fetchTripJob: Job? = null

    /**
     * Cache of trips. Key is [TimeTableState.JourneyCardInfo.journeyId] and value is
     * [TimeTableState.JourneyCardInfo].
     *
     * This list will be displayed in the UI.
     */
    private val journeys: MutableMap<String, TimeTableState.JourneyCardInfo> = mutableMapOf()

    fun onEvent(event: TimeTableUiEvent) {
        when (event) {
            is TimeTableUiEvent.LoadTimeTable -> onLoadTimeTable(event.trip)
            is TimeTableUiEvent.JourneyCardClicked -> onJourneyCardClicked(event.journeyId)
            TimeTableUiEvent.SaveTripButtonClicked -> onSaveTripButtonClicked()
            TimeTableUiEvent.ReverseTripButtonClicked -> onReverseTripButtonClicked()
            TimeTableUiEvent.RetryButtonClicked -> onLoadTimeTable(tripInfo!!)
            is TimeTableUiEvent.DateTimeSelectionChanged -> {
                onDateTimeSelectionChanged(item = event.dateTimeSelectionItem)
            }
        }
    }

    private fun onDateTimeSelectionChanged(item: DateTimeSelectionItem?) {
        println("DateTimeSelectionChanged: $item")
        // Verify if date time selection has actually changed, otherwise, api will be called unnecessarily.
        if (dateTimeSelectionItem != item) {
            updateUiState { copy(isLoading = true) }
            dateTimeSelectionItem = item
            journeys.clear() // Clear cache trips when date time selection changed.
            sandook.clearAllServiceAlerts()
            rateLimiter.triggerEvent()
        }
    }

    private fun fetchTrip() {
        println("fetchTrip API Call")
        fetchTripJob?.cancel()
        updateUiState { copy(silentLoading = true) }
        fetchTripJob = viewModelScope.launch(Dispatchers.IO) {
            rateLimiter.rateLimitFlow {
                println("rateLimitFlow block obj:$rateLimiter and coroutine: $this")
                updateUiState { copy(silentLoading = true) }
                loadTrip()
            }.catch { e ->
                println("Error while fetching trip: $e")
            }.collectLatest { result ->
                updateUiState { copy(silentLoading = false) }
                result.onSuccess { response ->
                    updateTripsCache(response)
                    updateUiStateWithFilteredTrips()
                }.onFailure {
                    updateUiState { copy(isLoading = false, isError = true) }
                }
            }
        }
    }

    // TODO - Write UT for this method
    private suspend fun updateTripsCache(response: TripResponse) = withContext(Dispatchers.IO) {
        val newJourneyList = response.buildJourneyList()
        val startedJourneyList = journeys.values
            .filter {
                // Find list of journeys that have started.
                it.hasJourneyStarted
            }
            .filterNot {
                // If a journey has ended then remove it from the cache.
                // This is to avoid displaying ended journeys.
                // The threshold time is set to 10 minutes.
                val thresholdTime = Clock.System.now().minus(JOURNEY_ENDED_CACHE_THRESHOLD_TIME)
                Instant.parse(it.destinationUtcDateTime).isBefore(thresholdTime)
            }
            .filterNot {
                // Those trips that are started / saved in cache but still part of new api data.
                newJourneyList?.any { newJourney -> newJourney.journeyId == it.journeyId } == true
            }
            .sortedBy {
                // Sort by chronological order, from earliest to latest
                Instant.parse(it.originUtcDateTime)
            }
            .takeLast(MAX_STARTED_JOURNEY_DISPLAY_THRESHOLD)

        // Clear all journeys and re-create using started trips(cache) and new api data.
        journeys.clear()
        newJourneyList?.associateBy { it.journeyId }?.let { journeys.putAll(it) }
        journeys.putAll(startedJourneyList.associateBy { it.journeyId })

        startedJourneyList.forEach {
            println(
                "TripsCache - Started tripCode:[${it.journeyId}], Time: ${
                    it.originUtcDateTime.utcToLocalDateTimeAEST().toHHMM()
                }"
            )
        }
        newJourneyList?.forEach {
            println(
                "TripsCache - New tripCode:[${it.journeyId}], Time: ${
                    it.originUtcDateTime.utcToLocalDateTimeAEST().toHHMM()
                }"
            )
        }
    }

    private fun updateUiStateWithFilteredTrips() {
        updateUiState {
            copy(
                isLoading = false,
                journeyList = updateJourneyCardInfoTimeText(journeys.values.toList())
                    .sortedBy { it.originUtcDateTime.utcToLocalDateTimeAEST() }
                    .toImmutableList(),
                isError = false,
            )
        }
    }

    private suspend fun loadTrip(): Result<TripResponse> = withContext(Dispatchers.IO) {
        println("loadTrip API Call")
        require(
            tripInfo != null && tripInfo!!.fromStopId.isNotEmpty() && tripInfo!!.toStopId.isNotEmpty(),
        ) { "Trip Info is null or empty" }

        runCatching {
            val tripResponse = tripPlanningService.trip(
                originStopId = tripInfo!!.fromStopId,
                destinationStopId = tripInfo!!.toStopId,
                date = dateTimeSelectionItem?.toYYYYMMDD(),
                time = dateTimeSelectionItem?.toHHMM(),
                depArr = when (dateTimeSelectionItem?.option) {
                    JourneyTimeOptions.LEAVE -> DepArr.DEP
                    JourneyTimeOptions.ARRIVE -> DepArr.ARR
                    else -> DepArr.DEP
                }
            )
            Result.success(tripResponse)
        }.getOrElse { error ->
            Result.failure(error)
        }
    }

    private fun onSaveTripButtonClicked() {
        println("Save Trip Button Clicked")
        viewModelScope.launch(Dispatchers.IO) {
            tripInfo?.let { trip ->
                println("Toggle Save Trip: $trip")
                val savedTrip = sandook.selectTripById(tripId = trip.tripId)
                if (savedTrip != null) {
                    // Trip is already saved, so delete it
                    sandook.deleteTrip(tripId = trip.tripId)
                    println("Deleted Trip (Pref): $savedTrip")
                    updateUiState { copy(isTripSaved = false) }
                } else {
                    // Trip is not saved, so save it
                    sandook.insertOrReplaceTrip(
                        tripId = trip.tripId,
                        fromStopId = trip.fromStopId, fromStopName = trip.fromStopName,
                        toStopId = trip.toStopId, toStopName = trip.toStopName
                    )
                    println("Saved Trip (Pref): $trip")
                    updateUiState { copy(isTripSaved = true) }
                }
            }
        }
    }

    private fun onJourneyCardClicked(journeyId: String) {
        println("Journey Card Clicked(JourneyId): $journeyId")
        _expandedJourneyId.update { if (it == journeyId) null else journeyId }
    }

    private fun onLoadTimeTable(trip: Trip) {
        println("onLoadTimeTable -- Trigger fromStopItem: ${trip.fromStopId}, toStopItem: ${trip.toStopId}")
        tripInfo = trip
        val savedTrip = sandook.selectTripById(tripId = trip.tripId)
        println("Saved Trip[${trip.tripId}]: $savedTrip")
        updateUiState {
            copy(
                isLoading = true,
                trip = trip,
                isTripSaved = savedTrip != null,
                isError = false,
            )
        }
        rateLimiter.triggerEvent()
    }

    private fun onReverseTripButtonClicked() {
        println("Reverse Trip Button Clicked -- Trigger")
        require(tripInfo != null) { "Trip Info is null" }
        val reverseTrip = Trip(
            fromStopId = tripInfo!!.toStopId,
            fromStopName = tripInfo!!.toStopName,
            toStopId = tripInfo!!.fromStopId,
            toStopName = tripInfo!!.fromStopName,
        )
        tripInfo = reverseTrip
        journeys.clear() // Clear cache trips when reverse trip is clicked.
        sandook.clearAllServiceAlerts() // Clear alerts cache when reverse trip is clicked.

        val savedTrip = sandook.selectTripById(tripId = reverseTrip.tripId)
        updateUiState {
            copy(
                trip = reverseTrip,
                isTripSaved = savedTrip != null,
                isLoading = true,
                isError = false,
            )
        }
        rateLimiter.triggerEvent()
    }

    /**
     * As the clock is progressing, the value [TimeTableState.JourneyCardInfo.timeText] of the
     * journey card should be updated.
     */
    private fun updateTimeText() = viewModelScope.launch {
        val updatedJourneyList = withContext(Dispatchers.IO) {
            updateJourneyCardInfoTimeText(_uiState.value.journeyList).toImmutableList()
        }
        updateUiState { copy(journeyList = updatedJourneyList) }
    }

    /**
     * Update the [TimeTableState.JourneyCardInfo.timeText] of the journey card.
     * This will be called when the screen is visible and the time is progressing and also when the
     * API returned with new data.
     */
    private fun updateJourneyCardInfoTimeText(
        journeyList: List<TimeTableState.JourneyCardInfo>,
    ): List<TimeTableState.JourneyCardInfo> {
        return journeyList.map { journeyCardInfo ->
            journeyCardInfo.copy(
                timeText = calculateTimeDifferenceFromNow(
                    utcDateString = journeyCardInfo.originUtcDateTime,
                ).toGenericFormattedTimeString()
            )
        }
    }

    private inline fun updateUiState(block: TimeTableState.() -> TimeTableState) {
        _uiState.update(block)
    }

    fun fetchAlertsForJourney(journeyId: String, onResult: (List<ServiceAlert>) -> Unit) {
        viewModelScope.launch {
            val alerts = withContext(Dispatchers.IO) {
                runCatching {
                    _uiState.value.journeyList.find { it.journeyId == journeyId }?.let { journey ->
                        getAlertsFromJourney(journey)
                    }.orEmpty()
                }.getOrElse { emptyList() }
            }
            onResult(alerts)
        }
    }

    private fun getAlertsFromJourney(journey: TimeTableState.JourneyCardInfo): List<ServiceAlert> {
        val alerts: List<ServiceAlert> =
            journey.legs.filterIsInstance<TimeTableState.JourneyCardInfo.Leg.TransportLeg>()
                .flatMap { it.serviceAlertList.orEmpty() }
        sandook.insertOrReplaceServiceAlert(
            journeyId = journey.journeyId,
            serviceAlerts = alerts,
        )
        return alerts
    }

    override fun onCleared() {
        super.onCleared()
        // TODO - ideally AlertsCache should be scoped to TimeTableDestination and object must be deleted itself.
        //   so we do not need to manually clear.
        sandook.clearAllServiceAlerts()
    }

    companion object {
        private const val ANR_TIMEOUT = 5000L
        private val REFRESH_TIME_TEXT_DURATION = 10.seconds
        private val AUTO_REFRESH_TIME_TABLE_DURATION = 30.seconds
        private val STOP_TIME_TEXT_UPDATES_THRESHOLD = 3.seconds

        /**
         * Maximum number of started journeys to display.
         */
        // TODO - UT - at-least these many should remain in past all the time once initial
        //  past trips are starting to show.
        private const val MAX_STARTED_JOURNEY_DISPLAY_THRESHOLD = 2

        /**
         * How long to keep displaying a past journey after it has ended.
         */
        private val JOURNEY_ENDED_CACHE_THRESHOLD_TIME = 10.minutes
    }
}
