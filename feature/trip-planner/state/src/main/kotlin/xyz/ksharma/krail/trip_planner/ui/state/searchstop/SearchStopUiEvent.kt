package xyz.ksharma.krail.trip_planner.ui.state.searchstop

import xyz.ksharma.krail.trip_planner.ui.state.searchstop.model.StopItem

sealed interface SearchStopUiEvent {
    data class SearchTextChanged(val query: String) : SearchStopUiEvent
}
