package xyz.ksharma.krail.trip_planner.ui.state.timetable

sealed interface TimeTableUiEvent {
    data class LoadTimeTable(val fromStopId: String, val toStopId: String) : TimeTableUiEvent
    data class JourneyCardClicked(val journeyCardInfo: TimeTableState.JourneyCardInfo) :
        TimeTableUiEvent
}
