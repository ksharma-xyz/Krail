package xyz.ksharma.krail.trip_planner.ui.timetable

import xyz.ksharma.krail.trip_planner.domain.model.toTransportModeType
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
import xyz.ksharma.krail.trip_planner.domain.DateTimeHelper.aestToHHMM
import xyz.ksharma.krail.trip_planner.domain.DateTimeHelper.formatTo12HourTime
import xyz.ksharma.krail.trip_planner.domain.DateTimeHelper.utcToAEST
import xyz.ksharma.krail.trip_planner.network.api.model.TripResponse
import xyz.ksharma.krail.trip_planner.network.api.repository.TripPlanningRepository
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableState
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
                            journeyList = response.journeys?.map { journey ->

                                // TODO -
                                //  1. Sanitise data in domain layer.
                                //  2. Pass non null items only to display in ViewModel.

                                val firstLeg = journey.legs?.firstOrNull()
                                val lastLeg = journey.legs?.lastOrNull()

                                val originTime = firstLeg?.origin?.departureTimeEstimated ?: firstLeg?.origin?.departureTimePlanned
                                val arrivalTime  = lastLeg?.destination?.arrivalTimeEstimated
                                    ?: lastLeg?.destination?.arrivalTimePlanned

                                TimeTableState.JourneyCardInfo(
                                    timeText = originTime?.utcToAEST()?.aestToHHMM() ?: "NULL,",
                                    platformText = firstLeg?.stopSequence?.firstOrNull()?.disassembledName
                                        ?: "NULL",
                                    originTime = originTime?.utcToAEST()?.aestToHHMM() ?: "NULL",
                                    destinationTime = arrivalTime?.utcToAEST()?.aestToHHMM() ?: "NULL",
                                    travelTime = "${
                                        ((journey.legs?.mapNotNull { it.duration }?.sum()) ?: 0)
                                                % 60
                                    } min",
                                    transportModeLines = journey.legs?.mapNotNull { leg ->
                                        leg.transportation?.product?.productClass?.toInt()?.toTransportModeType()?.let { transportModeType ->
                                            TimeTableState.TransportModeLine(
                                                transportModeType = transportModeType,
                                                lineName = leg.transportation?.disassembledName ?: "NULL"
                                            )
                                        }
                                    }?.toImmutableList() ?: persistentListOf(),
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
    private fun List<TripResponse.StopSequence>.interchangeStopsList() = this.mapNotNull {
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
