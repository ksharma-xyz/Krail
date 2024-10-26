package xyz.ksharma.krail.trip.planner.ui.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.ksharma.krail.core.datetime.DateTimeHelper.calculateTimeDifferenceFromNow
import xyz.ksharma.krail.core.datetime.DateTimeHelper.toGenericFormattedTimeString
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.sandook.di.SandookFactory
import xyz.ksharma.krail.trip.planner.network.api.repository.TripPlanningRepository
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
) : ViewModel() {

    private val sandook: Sandook = sandookFactory.create(SandookFactory.SandookKey.SAVED_TRIP)

    private val _uiState: MutableStateFlow<TimeTableState> = MutableStateFlow(TimeTableState())
    val uiState: StateFlow<TimeTableState> = _uiState

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> =
        _isLoading.stateIn(viewModelScope, SharingStarted.WhileSubscribed(ANR_TIMEOUT), true)

    private val _isActive: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isActive: StateFlow<Boolean> = _isActive.onStart {
        while (true) {
            updateTimeText()
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
        }
    }

    private fun onSaveTripButtonClicked() {
        Timber.d("Save Trip Button Clicked")
        tripInfo?.let { trip ->
            Timber.d("Save Trip: $trip")
            sandook.putString(key = trip.tripId, value = trip.toJsonString())
            sandook.getString(key = trip.tripId)?.let { savedTrip ->
                Timber.d("Saved Trip (Pref): ${Trip.fromJsonString(savedTrip)}")
            }
            updateUiState { copy(isTripSaved = true) }
        }
    }

    private fun onJourneyCardClicked(journeyId: String) {
        Timber.d("Journey Card Clicked(JourneyId): $journeyId")
        _expandedJourneyId.update { if (it == journeyId) null else journeyId }
    }

    private fun onLoadTimeTable(tripInfo: Trip) = with(tripInfo) {
        Timber.d("loadTimeTable API Call- fromStopItem: $fromStopId, toStopItem: $toStopId")
        this@TimeTableViewModel.tripInfo = this
        updateUiState { copy(isLoading = true) }

        viewModelScope.launch {
            require(!(fromStopId.isEmpty() || toStopId.isEmpty())) { "Invalid Stop Ids" }
            tripRepository.trip(originStopId = fromStopId, destinationStopId = toStopId)
                .onSuccess { response ->
                    updateUiState {
                        copy(
                            isLoading = false,
                            journeyList = response.buildJourneyList() ?: persistentListOf(),
                        )
                    }
                    response.logForUnderstandingData()
                }.onFailure {
                    Timber.e("Error while fetching trip: $it")
                }
        }
    }

    /**
     * As the clock is progressing, the value [TimeTableState.JourneyCardInfo.timeText] of the
     * journey card should be updated.
     */
    private fun updateTimeText() {
        updateUiState {
            copy(
                journeyList = journeyList.map { journeyCardInfo ->
                    journeyCardInfo.copy(
                        timeText = calculateTimeDifferenceFromNow(
                            utcDateString = journeyCardInfo.originUtcDateTime,
                        ).toGenericFormattedTimeString(),
                    )
                }.toImmutableList(),
            )
        }
        Timber.d("New Time: ${uiState.value.journeyList.joinToString(", ") { it.timeText }}")
    }

    private inline fun updateUiState(block: TimeTableState.() -> TimeTableState) {
        _uiState.update(block)
    }

    companion object {
        private const val ANR_TIMEOUT = 5000L
        private val REFRESH_TIME_TEXT_DURATION = 5.seconds
        private val STOP_TIME_TEXT_UPDATES_THRESHOLD = 3.seconds
    }
}
