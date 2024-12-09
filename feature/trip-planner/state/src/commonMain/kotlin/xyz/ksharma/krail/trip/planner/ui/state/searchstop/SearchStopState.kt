package xyz.ksharma.krail.trip.planner.ui.state.searchstop

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode

data class SearchStopState(
    val stops: ImmutableList<StopResult> = persistentListOf(),
    val isError: Boolean = false,
    val isLoading: Boolean = false,
) {
    data class StopResult(
        val stopName: String,
        val stopId: String,
        var transportModeType: ImmutableList<TransportMode> = persistentListOf(),
    )
}
