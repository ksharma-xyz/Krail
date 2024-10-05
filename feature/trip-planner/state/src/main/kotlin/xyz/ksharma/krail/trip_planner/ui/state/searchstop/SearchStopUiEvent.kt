package xyz.ksharma.krail.trip_planner.ui.state.searchstop

sealed interface SearchStopUiEvent {
    data class SearchStopFromQuery(val query: String) : SearchStopUiEvent
}
