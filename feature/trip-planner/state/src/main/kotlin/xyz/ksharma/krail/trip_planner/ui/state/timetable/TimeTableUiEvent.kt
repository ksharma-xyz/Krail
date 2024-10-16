package xyz.ksharma.krail.trip_planner.ui.state.timetable

sealed interface TimeTableUiEvent {
    data object SaveTripButtonClicked : TimeTableUiEvent
    data class LoadTimeTable(val trip : Trip) : TimeTableUiEvent
    data class JourneyCardClicked(val journeyId: String) : TimeTableUiEvent
}
