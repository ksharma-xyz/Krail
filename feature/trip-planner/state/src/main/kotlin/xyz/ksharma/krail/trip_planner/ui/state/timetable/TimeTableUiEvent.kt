package xyz.ksharma.krail.trip_planner.ui.state.timetable

sealed interface TimeTableUiEvent {
    data object LoadTimeTable : TimeTableUiEvent
}
