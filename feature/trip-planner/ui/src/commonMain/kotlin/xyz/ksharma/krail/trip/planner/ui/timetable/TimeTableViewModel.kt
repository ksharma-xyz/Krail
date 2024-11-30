package xyz.ksharma.krail.trip.planner.ui.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.persistentListOf
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
import xyz.ksharma.krail.core.datetime.DateTimeHelper.calculateTimeDifferenceFromNow
import xyz.ksharma.krail.core.datetime.DateTimeHelper.toGenericFormattedTimeString
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

    private val _expandedJourneyId: MutableStateFlow<String?> = MutableStateFlow(null)
    val expandedJourneyId: StateFlow<String?> = _expandedJourneyId

    private var tripInfo: Trip? = null
    private var dateTimeSelectionItem: DateTimeSelectionItem? = null

    private var fetchTripJob: Job? = null

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
        dateTimeSelectionItem = item
        rateLimiter.triggerEvent()
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
                    // println("Success API response")
                    updateUiState {
                        copy(
                            isLoading = false,
                            journeyList = response.buildJourneyList() ?: persistentListOf(),
                            isError = false,
                        )
                    }
                    //response.logForUnderstandingData()
                }.onFailure {
                    // Timber.e("Error while fetching trip: $it")
                    updateUiState { copy(isLoading = false, isError = true) }
                }
            }
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
            _uiState.value.journeyList.map { journeyCardInfo ->
                journeyCardInfo.copy(
                    timeText = calculateTimeDifferenceFromNow(
                        utcDateString = journeyCardInfo.originUtcDateTime,
                    ).toGenericFormattedTimeString(),
                )
            }.toImmutableList()
        }

        updateUiState {
            copy(journeyList = updatedJourneyList)
        }

        // println("New Time: ${uiState.value.journeyList.joinToString(", ") { it.timeText }}")
    }

    private inline fun updateUiState(block: TimeTableState.() -> TimeTableState) {
        _uiState.update(block)
    }

    fun fetchAlertsForJourney(journeyId: String, onResult: (Set<ServiceAlert>) -> Unit) {
        viewModelScope.launch {
            val alerts = withContext(Dispatchers.IO) {
                runCatching {
                    _uiState.value.journeyList.find { it.journeyId == journeyId }?.let { journey ->
                        getAlertsFromJourney(journey)
                    }.orEmpty()
                }.getOrElse { emptySet() }
            }
            onResult(alerts)
        }
    }

    private fun getAlertsFromJourney(journey: TimeTableState.JourneyCardInfo): Set<ServiceAlert> {
        return journey.legs.filterIsInstance<TimeTableState.JourneyCardInfo.Leg.TransportLeg>()
            .flatMap { it.serviceAlertList.orEmpty() }.toSet()
    }

    companion object {
        private const val ANR_TIMEOUT = 5000L
        private val REFRESH_TIME_TEXT_DURATION = 5.seconds
        private val STOP_TIME_TEXT_UPDATES_THRESHOLD = 3.seconds
    }
}
