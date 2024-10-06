package xyz.ksharma.krail.trip_planner.ui.state.searchstop

import xyz.ksharma.krail.trip_planner.domain.model.TransportMode

sealed interface SearchStopUiEvent {

    data class SearchTextChanged(val query: String) : SearchStopUiEvent

    data class StopSelected(
        val stopId: String,
        val stopName: String,
        val mode: List<TransportMode.TransportModeType>,
    ) : SearchStopUiEvent
}
