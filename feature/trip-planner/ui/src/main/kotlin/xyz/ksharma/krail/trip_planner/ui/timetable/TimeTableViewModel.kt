package xyz.ksharma.krail.trip_planner.ui.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.ksharma.krail.core.date_time.DateTimeHelper.calculateTimeDifferenceFromNow
import xyz.ksharma.krail.core.date_time.DateTimeHelper.toFormattedString
import xyz.ksharma.krail.trip_planner.network.api.repository.TripPlanningRepository
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableState
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableUiEvent
import xyz.ksharma.krail.trip_planner.ui.timetable.business.buildJourneyList
import xyz.ksharma.krail.trip_planner.ui.timetable.business.logForUnderstandingData
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class TimeTableViewModel @Inject constructor(
    private val tripRepository: TripPlanningRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<TimeTableState> = MutableStateFlow(TimeTableState())
    val uiState: StateFlow<TimeTableState> = _uiState

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> =
        _isLoading.stateIn(viewModelScope, SharingStarted.WhileSubscribed(ANR_TIMEOUT), true)

    fun onEvent(event: TimeTableUiEvent) {
        when (event) {
            is TimeTableUiEvent.LoadTimeTable -> onLoadTimeTable(event.fromStopId, event.toStopId)
        }
    }

    private fun onLoadTimeTable(fromStopId: String?, toStopId: String?) {
        Timber.d("loadTimeTable API Call- fromStopItem: $fromStopId, toStopItem: $toStopId")

        updateUiState { copy(isLoading = true) }

        viewModelScope.launch {
            require(!(fromStopId.isNullOrEmpty() || toStopId.isNullOrEmpty())) { "Invalid Stop Ids" }
            tripRepository.trip(originStopId = fromStopId, destinationStopId = toStopId)
                .onSuccess { response ->

                    // TODO -
                    //   1. Create UI Model
                    //   2. Update UI State

                    updateUiState {
                        copy(
                            isLoading = false,
                            journeyList = response.buildJourneyList() ?: persistentListOf()
                        )
                    }

                    response.logForUnderstandingData()
                    updateTimeTextPeriodically()
                }.onFailure {
                    Timber.e("Error while fetching trip: $it")
                }
        }
    }

    private fun updateTimeTextPeriodically() {
        // TODO - this will keep running even if app is in background as long as the ViewModel is alive. Need to fix this.
        //  Use kotlin.concurrent.fixedRateTimer
        viewModelScope.launch {
            flow {
                while (true) {
                    emit(Unit)
                    delay(10.seconds) // variation factor
                }
            }.collect {
                updateUiState {
                    copy(
                        journeyList = journeyList.map { journeyCardInfo ->
                            journeyCardInfo.copy(
                                timeText = journeyCardInfo.originUtcDateTime.let {
                                    val x = calculateTimeDifferenceFromNow(utcDateString = it).toFormattedString()
                                    Timber.d("\tupdate: $x")
                                    x
                                }
                            )
                        }.toImmutableList()
                    )
                }
            }
        }
    }

    private inline fun updateUiState(block: TimeTableState.() -> TimeTableState) {
        _uiState.update(block)
    }

    companion object {
        private const val ANR_TIMEOUT = 5000L
    }
}
