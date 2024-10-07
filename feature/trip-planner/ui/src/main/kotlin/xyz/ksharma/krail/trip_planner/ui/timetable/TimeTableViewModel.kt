package xyz.ksharma.krail.trip_planner.ui.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.ksharma.krail.trip_planner.domain.DateTimeHelper.calculateTimeDifference
import xyz.ksharma.krail.trip_planner.domain.DateTimeHelper.formatTo12HourTime
import xyz.ksharma.krail.trip_planner.domain.DateTimeHelper.utcToAEST
import xyz.ksharma.krail.trip_planner.network.api.model.TripResponse
import xyz.ksharma.krail.trip_planner.network.api.repository.TripPlanningRepository
import xyz.ksharma.krail.trip_planner.ui.state.savedtrip.SavedTripsState
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableState
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableState.Journey
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableUiEvent
import javax.inject.Inject

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
        viewModelScope.launch {
            require(!(fromStopId.isNullOrEmpty() || toStopId.isNullOrEmpty())) { "Invalid Stop Ids" }
            tripRepository.trip(originStopId = fromStopId, destinationStopId = toStopId)
                .onSuccess { response ->

                    // TODO -
                    //   1. Create UI Model
                    //   2. Update UI State

                    updateUiState {
                        copy(
                            journeyList = response.journeys?.map { journey ->

                                val origin =
                                    journey.legs?.firstOrNull()?.origin?.departureTimeEstimated?.utcToAEST()
                                        ?.formatTo12HourTime()
                                val destination =
                                    journey.legs?.lastOrNull()?.destination?.arrivalTimeEstimated?.utcToAEST()
                                        ?.formatTo12HourTime()

                                val timeDifference = calculateTimeDifference(
                                    startDate = journey.legs?.lastOrNull()?.destination?.arrivalTimeEstimated ?: "",
                                    endDate = journey.legs?.lastOrNull()?.destination?.arrivalTimeEstimated ?: "",
                                )

                                Journey(
                                    departureText = "in x mins on Platform X",
                                    timeText = "$origin - $destination ($timeDifference)",
                                    transportModeLineList = persistentListOf(),
                                )

                            }?.toImmutableList() ?: persistentListOf()
                        )
                    }

                    Timber.d("Journeys: ${response.journeys?.size}")
                    response.journeys?.mapIndexed { jindex, j ->
                        Timber.d("JOURNEY #${jindex + 1}")
                        j.legs?.forEachIndexed { index, leg ->
                            Timber.d(" LEG#${index + 1}")
                            Timber.d(
                                "\t\t ORG: ${
                                    leg.origin?.departureTimeEstimated?.utcToAEST()
                                        ?.formatTo12HourTime()
                                }," +
                                        " DEST: ${
                                            leg.destination?.arrivalTimeEstimated?.utcToAEST()
                                                ?.formatTo12HourTime()
                                        }, " +
                                        //     "Duration: ${leg.duration}, " +
                                        // "transportation: ${leg.transportation?.name}",
                                        "interchange: ${leg.interchange?.run { "[desc:$desc, type:$type] " }}" +
                                        // "leg properties: ${leg.properties}" +
                                        //"leg origin properties: ${leg.origin?.properties}"
                                        "\n\t\t\t leg stopSequence: ${leg.stopSequence?.interchangeStopsList()}"
                            )
                        }
                    }
                }.onFailure {
                    Timber.e("Error while fetching trip: $it")
                }
        }
    }

    /**
     * Prints the stops for legs when interchange required.
     */
    private fun List<TripResponse.StopSequenceClass>.interchangeStopsList() = this.mapNotNull {
        // TODO - figure role of ARR vs DEP time
        val timeArr = it.arrivalTimeEstimated?.utcToAEST()
            ?.formatTo12HourTime() ?: it.arrivalTimePlanned?.utcToAEST()?.formatTo12HourTime()

        val depTime = it.departureTimeEstimated?.utcToAEST()
            ?.formatTo12HourTime() ?: it.departureTimePlanned?.utcToAEST()?.formatTo12HourTime()

        if (timeArr == null && depTime == null) null else "\n\t\t\t\t Stop: ${it.name}, depTime: ${timeArr ?: depTime}"
    }

    private inline fun updateUiState(block: TimeTableState.() -> TimeTableState) {
        _uiState.update(block)
    }

    companion object {
        private const val ANR_TIMEOUT = 5000L
    }
}
