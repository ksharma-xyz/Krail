package xyz.ksharma.core.test

import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.test.runTest
import xyz.ksharma.core.test.fakes.FakeFlag
import xyz.ksharma.core.test.fakes.FakeSandook
import xyz.ksharma.core.test.fakes.FakeTripPlanningService
import xyz.ksharma.krail.core.remote_config.flag.FlagKeys
import xyz.ksharma.krail.core.remote_config.flag.FlagValue
import xyz.ksharma.krail.sandook.SelectProductClassesForStop
import xyz.ksharma.krail.trip.planner.ui.searchstop.RealStopResultsManager
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.searchstop.SearchStopState
import kotlin.test.Test
import kotlin.test.assertEquals

// TODO Write UTs separately
class RealStopResultsManagerTest {

    private lateinit var tripPlanningService: FakeTripPlanningService
    private lateinit var sandook: FakeSandook
    private lateinit var flag: FakeFlag
    private lateinit var stopResultsManager: RealStopResultsManager

    fun setUp() {
        tripPlanningService = FakeTripPlanningService()
        sandook = FakeSandook()
        flag = FakeFlag()
        stopResultsManager = RealStopResultsManager(tripPlanningService, sandook, flag)
    }
/*

    @Test
    fun `fetchStopResults should fetch from local stops when enabled`() = runTest {
        setUp()

        // Set flag values
        flag.setFlagValue(FlagKeys.LOCAL_STOPS_ENABLED.key, FlagValue.BooleanValue(true))
        flag.setFlagValue(
            FlagKeys.HIGH_PRIORITY_STOP_IDS.key,
            FlagValue.JsonValue("[\"200060\", \"200070\"]")
        )

        // Set Sandook response
        sandook.insertNswStop(stopId = "200060", stopName = "Stop A", stopLat = 1.0, stopLon = 1.0)

        // Call the method
        val results = stopResultsManager.fetchStopResults("query")

        // Verify the results
        assertEquals(1, results.size)
        assertEquals("200060", results[0].stopId)
        assertEquals("Stop A", results[0].stopName)
    }
*/

/*
    @Test
    fun `fetchStopResults should fetch from remote when local stops are disabled`() = runTest {
        setUp()

        // Set flag values
        flag.setFlagValue(FlagKeys.LOCAL_STOPS_ENABLED.key, FlagValue.BooleanValue(false))
        flag.setFlagValue(
            FlagKeys.HIGH_PRIORITY_STOP_IDS.key,
            FlagValue.JsonValue("[\"200060\", \"200070\"]")
        )

        // Set TripPlanningService response
        tripPlanningService.stopFinderResponse = listOf(
            SearchStopState.StopResult(
                "200080",
                "Stop B",
                listOf(TransportMode.Bus().toTransportModeType()).toImmutableList()
            )
        )

        // Call the method
        val results = stopResultsManager.fetchStopResults("query")

        // Verify the results
        assertEquals(1, results.size)
        assertEquals("200080", results[0].stopId)
        assertEquals("Stop B", results[0].stopName)
    }

    @Test
    fun `prioritiseStops should prioritize stops correctly`() {
        setUp()

        // Set flag values
        flag.setFlagValue(
            FlagKeys.HIGH_PRIORITY_STOP_IDS.key,
            FlagValue.JsonValue("[\"200060\", \"200070\"]")
        )

        // Create test data
        val stopResults = listOf(
            createStopResult("200060", "Stop A", listOf(TransportMode.Train())),
            createStopResult("200080", "Stop B", listOf(TransportMode.Bus())),
            createStopResult("200070", "Stop C", listOf(TransportMode.Ferry())),
            createStopResult("200090", "Stop D", listOf(TransportMode.Train(), TransportMode.Bus()))
        )

        val expectedResults = listOf(
            createStopResult("200060", "Stop A", listOf(TransportMode.Train())),
            createStopResult("200070", "Stop C", listOf(TransportMode.Ferry())),
            createStopResult(
                "200090",
                "Stop D",
                listOf(TransportMode.Train(), TransportMode.Bus())
            ),
            createStopResult("200080", "Stop B", listOf(TransportMode.Bus()))
        )

        // Call the method
        val actualResults = stopResultsManager.prioritiseStops(stopResults)

        // Verify the results
        assertEquals(expectedResults, actualResults)
    }
*/

    private fun createStopResult(
        stopId: String,
        stopName: String,
        transportModes: List<TransportMode>,
    ): SearchStopState.StopResult {
        return SearchStopState.StopResult(
            stopId = stopId,
            stopName = stopName,
            transportModeType = transportModes.toImmutableList()
        )
    }
}
