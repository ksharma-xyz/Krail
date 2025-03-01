package xyz.ksharma.core.test.fakes

import xyz.ksharma.krail.trip.planner.ui.searchstop.StopResultsManager
import xyz.ksharma.krail.trip.planner.ui.state.searchstop.SearchStopState

class FakeStopResultsManager : StopResultsManager {

    override suspend fun fetchStopResults(query: String): List<SearchStopState.StopResult> {
        TODO("Not yet implemented")
    }

    override fun prioritiseStops(stopResults: List<SearchStopState.StopResult>): List<SearchStopState.StopResult> {
        TODO("Not yet implemented")
    }
}
