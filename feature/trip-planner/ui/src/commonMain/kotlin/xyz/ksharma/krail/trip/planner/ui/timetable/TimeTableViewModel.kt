package xyz.ksharma.krail.trip.planner.ui.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
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
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.sandook.di.SandookFactory
import xyz.ksharma.krail.trip.planner.network.api.RateLimiter
import xyz.ksharma.krail.trip.planner.network.api.model.TripResponse
import xyz.ksharma.krail.trip.planner.network.api.repository.TripPlanningRepository
import xyz.ksharma.krail.trip.planner.ui.state.alerts.ServiceAlert
import xyz.ksharma.krail.trip.planner.ui.state.timetable.TimeTableState
import xyz.ksharma.krail.trip.planner.ui.state.timetable.TimeTableUiEvent
import xyz.ksharma.krail.trip.planner.ui.state.timetable.Trip
import xyz.ksharma.krail.trip.planner.ui.timetable.business.buildJourneyList
import xyz.ksharma.krail.trip.planner.ui.timetable.business.logForUnderstandingData
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class TimeTableViewModel @Inject constructor(
    private val tripRepository: TripPlanningRepository,
    sandookFactory: SandookFactory,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val rateLimiter: RateLimiter,
) : ViewModel() {

    private val sandook: Sandook = sandookFactory.create(SandookFactory.SandookKey.SAVED_TRIP)

    private val _uiState: MutableStateFlow<TimeTableState> = MutableStateFlow(TimeTableState())
    val uiState: StateFlow<TimeTableState> = _uiState

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
        // Will start fetching the trip as soon as the screen is visible, which means if android-app goes
        // to background and come back up again, the API call will be made.
        // Probably good to have data up to date.
        .onStart {
            Timber.d("onStart: Fetching Trip")
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

    fun onEvent(event: TimeTableUiEvent) {
        when (event) {
            is TimeTableUiEvent.LoadTimeTable -> onLoadTimeTable(event.trip)
            is TimeTableUiEvent.JourneyCardClicked -> onJourneyCardClicked(event.journeyId)
            TimeTableUiEvent.SaveTripButtonClicked -> onSaveTripButtonClicked()
            TimeTableUiEvent.ReverseTripButtonClicked -> onReverseTripButtonClicked()
            TimeTableUiEvent.RetryButtonClicked -> onLoadTimeTable(tripInfo!!)
        }
    }

    private fun fetchTrip() {
        Timber.d("fetchTrip API Call")
        viewModelScope.launch(ioDispatcher) {
            // TODO - silent refresh here, UI to display loading but silent one.
            rateLimiter.rateLimitFlow {
                Timber.d("rateLimitFlow block")
                loadTrip()
            }.catch { e ->
                Timber.e("Error while fetching trip: $e")
            }.collectLatest { result ->
                result.onSuccess { response ->
                    Timber.d("Success API response")
                    updateUiState {
                        copy(
                            isLoading = false,
                            journeyList = response.buildJourneyList() ?: persistentListOf(),
                            isError = false,
                        )
                    }
                    response.logForUnderstandingData()
                }.onFailure {
                    Timber.e("Error while fetching trip: $it")
                    updateUiState { copy(isLoading = false, isError = true) }
                }
            }
        }
    }

    private suspend fun loadTrip(): Result<TripResponse> = withContext(ioDispatcher) {
        Timber.d("loadTrip API Call")
        require(
            tripInfo != null && tripInfo!!.fromStopId.isNotEmpty() && tripInfo!!.toStopId.isNotEmpty(),
        ) { "Trip Info is null or empty" }

        tripRepository.trip(
            originStopId = tripInfo!!.fromStopId,
            destinationStopId = tripInfo!!.toStopId,
        )
    }

    private fun onSaveTripButtonClicked() {
        Timber.d("Save Trip Button Clicked")
        viewModelScope.launch(ioDispatcher) {
            tripInfo?.let { trip ->
                Timber.d("Toggle Save Trip: $trip")
                val savedTrip = sandook.getString(key = trip.tripId)
                if (savedTrip != null) {
                    // Trip is already saved, so delete it
                    sandook.remove(key = trip.tripId)
                    Timber.d("Deleted Trip (Pref): ${Trip.fromJsonString(savedTrip)}")
                    updateUiState { copy(isTripSaved = false) }
                } else {
                    // Trip is not saved, so save it
                    sandook.putString(key = trip.tripId, value = trip.toJsonString())
                    Timber.d("Saved Trip (Pref): $trip")
                    updateUiState { copy(isTripSaved = true) }
                }
            }
        }
    }

    private fun onJourneyCardClicked(journeyId: String) {
        Timber.d("Journey Card Clicked(JourneyId): $journeyId")
        _expandedJourneyId.update { if (it == journeyId) null else journeyId }
    }

    private fun onLoadTimeTable(trip: Trip) {
        Timber.d("onLoadTimeTable -- Trigger fromStopItem: ${trip.fromStopId}, toStopItem: ${trip.toStopId}")
        tripInfo = trip
        val savedTrip = sandook.getString(key = trip.tripId)
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
        Timber.d("Reverse Trip Button Clicked -- Trigger")
        require(tripInfo != null) { "Trip Info is null" }
        val reverseTrip = Trip(
            fromStopId = tripInfo!!.toStopId,
            fromStopName = tripInfo!!.toStopName,
            toStopId = tripInfo!!.fromStopId,
            toStopName = tripInfo!!.fromStopName,
        )
        tripInfo = reverseTrip
        val savedTrip = sandook.getString(key = reverseTrip.tripId)
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
        val updatedJourneyList = withContext(ioDispatcher) {
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

        // Timber.d("New Time: ${uiState.value.journeyList.joinToString(", ") { it.timeText }}")
    }

    private inline fun updateUiState(block: TimeTableState.() -> TimeTableState) {
        _uiState.update(block)
    }

    fun fetchAlertsForJourney(journeyId: String, onResult: (Set<ServiceAlert>) -> Unit) {
        viewModelScope.launch {
            val alerts = withContext(ioDispatcher) {
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
