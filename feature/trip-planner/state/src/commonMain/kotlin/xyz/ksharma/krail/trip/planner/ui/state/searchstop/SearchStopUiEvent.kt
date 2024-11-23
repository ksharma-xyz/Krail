package xyz.ksharma.krail.trip.planner.ui.state.searchstop

sealed interface SearchStopUiEvent {
    data class SearchTextChanged(val query: String) : SearchStopUiEvent
}
