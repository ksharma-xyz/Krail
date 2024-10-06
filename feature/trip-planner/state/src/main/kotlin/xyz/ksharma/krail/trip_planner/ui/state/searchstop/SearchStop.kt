package xyz.ksharma.krail.trip_planner.ui.state.searchstop

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import xyz.ksharma.krail.trip_planner.domain.StopResultMapper

data class SearchStopState(
    val stops: ImmutableList<StopResultMapper.StopResult> = persistentListOf(),
    val isError: Boolean = false,
    val isLoading: Boolean = false,
)
