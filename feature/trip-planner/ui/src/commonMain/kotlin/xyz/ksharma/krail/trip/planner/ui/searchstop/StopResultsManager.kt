package xyz.ksharma.krail.trip.planner.ui.searchstop

import xyz.ksharma.krail.trip.planner.ui.state.searchstop.SearchStopState

interface StopResultsManager {

    suspend fun fetchStopResults(query: String): List<SearchStopState.StopResult>

    fun prioritiseStops(stopResults: List<SearchStopState.StopResult>): List<SearchStopState.StopResult>
}
