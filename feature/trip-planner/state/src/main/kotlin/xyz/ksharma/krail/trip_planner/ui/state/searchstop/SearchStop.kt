package xyz.ksharma.krail.trip_planner.ui.state.searchstop

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SearchStopState(
    val stops: ImmutableList<String> = persistentListOf(),
    val isError: Boolean = false,
    val isLoading: Boolean = false,
)
