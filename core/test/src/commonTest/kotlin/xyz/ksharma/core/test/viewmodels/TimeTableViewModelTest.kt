package xyz.ksharma.core.test.viewmodels

import app.cash.turbine.test
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.Clock
import xyz.ksharma.core.test.fakes.FakeAnalytics
import xyz.ksharma.core.test.fakes.FakeRateLimiter
import xyz.ksharma.core.test.fakes.FakeSandook
import xyz.ksharma.core.test.fakes.FakeTripPlanningService
import xyz.ksharma.core.test.fakes.FakeTripResponseBuilder.buildTripResponse
import xyz.ksharma.krail.core.analytics.Analytics
import xyz.ksharma.krail.core.datetime.DateTimeHelper.formatTo12HourTime
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.trip.planner.network.api.model.TripResponse
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.TransportModeLine
import xyz.ksharma.krail.trip.planner.ui.state.timetable.TimeTableState
import xyz.ksharma.krail.trip.planner.ui.state.timetable.TimeTableState.JourneyCardInfo.Stop
import xyz.ksharma.krail.trip.planner.ui.state.timetable.TimeTableUiEvent
import xyz.ksharma.krail.trip.planner.ui.state.timetable.Trip
import xyz.ksharma.krail.trip.planner.ui.timetable.TimeTableViewModel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@OptIn(ExperimentalCoroutinesApi::class)
class TimeTableViewModelTest {

    private val sandook: Sandook = FakeSandook()
    private val analytics: Analytics = FakeAnalytics()
    private val tripPlanningService = FakeTripPlanningService()
    private val rateLimiter = FakeRateLimiter()
    private lateinit var viewModel: TimeTableViewModel

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = TimeTableViewModel(
            tripPlanningService = tripPlanningService,
            rateLimiter = rateLimiter,
            sandook = sandook,
            analytics = analytics,
            ioDispatcher = testDispatcher,
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN initial state WHEN observer is active THEN fetchTrip and trackScreenViewEvent should be called`() =
        runTest {
            // Ensure analytics events have not been tracked before observation
            assertFalse((analytics as FakeAnalytics).isEventTracked("view_screen"))

            viewModel.isLoading.test {
                val isLoadingState = awaitItem()
                assertEquals(isLoadingState, true)

                advanceUntilIdle()
                assertTrue(analytics.isEventTracked("view_screen"))

                cancelAndConsumeRemainingEvents()
            }
        }

    // region Test for fetchTrip / Trip API call

    @Test
    fun `GIVEN a trip WHEN LoadTimeTable event is triggered and Trip API is success response THEN UI State must update with journeyList`() =
        runTest {
            // GIVEN a trip
            val trip = Trip(
                fromStopId = "FROM_STOP_ID_1",
                fromStopName = "STOP_NAME_1",
                toStopId = "TO_STOP_ID_1",
                toStopName = "STOP_NAME_2"
            )
            tripPlanningService.isSuccess = true

            // THEN verify that the UI state is updated correctly
            viewModel.uiState.test {
                val initialState = awaitItem()
                initialState.run {
                    assertTrue(isLoading)
                    assertNull(initialState.trip)
                    assertFalse(isError)
                    assertFalse(isTripSaved)
                }

                // WHEN the LoadTimeTable event is triggered
                viewModel.onEvent(TimeTableUiEvent.LoadTimeTable(trip))
                viewModel.fetchTrip() // Manually call fetchTrip() to simulate the actual behavior
                awaitItem().run {
                    assertTrue(isLoading)
                    assertFalse(silentLoading)
                    assertFalse(isError)
                    assertTrue(journeyList.isEmpty())
                }

                // need to skip two items, because silentLoading will be toggled, as we manually call fetchTrip()
                skipItems(2)
                /*
                                awaitItem().run {
                                   assertTrue(silentLoading)
                                }
                                awaitItem().run {
                                    assertFalse(silentLoading)
                                }
                */

                awaitItem().run {
                    assertFalse(isLoading)
                    assertFalse(silentLoading)
                    assertTrue(journeyList.isNotEmpty())
                    assertEquals(expected = 1, journeyList.size)
                }

                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `GIVEN a trip WHEN LoadTimeTable event is triggered and Trip API is error response THEN UIState should have isError as true`() =
        runTest {
            // GIVEN a trip
            val trip = Trip(
                fromStopId = "FROM_STOP_ID_1",
                fromStopName = "STOP_NAME_1",
                toStopId = "TO_STOP_ID_1",
                toStopName = "STOP_NAME_2"
            )
            tripPlanningService.isSuccess = false

            // THEN verify that the UI state is updated correctly
            viewModel.uiState.test {
                val initialState = awaitItem()
                initialState.run {
                    assertTrue(isLoading)
                    assertNull(initialState.trip)
                    assertFalse(isError)
                    assertFalse(isTripSaved)
                }

                // WHEN the LoadTimeTable event is triggered
                viewModel.onEvent(TimeTableUiEvent.LoadTimeTable(trip))
                viewModel.fetchTrip() // Manually call fetchTrip() to simulate the actual behavior
                awaitItem().run {
                    assertTrue(isLoading)
                    assertFalse(silentLoading)
                    assertFalse(isError)
                    assertTrue(journeyList.isEmpty())
                }

                // need to skip two items, because silentLoading will be toggled, as we manually call fetchTrip()
                skipItems(2)
                /*
                                awaitItem().run {
                                   assertTrue(silentLoading)
                                }
                                awaitItem().run {
                                    assertFalse(silentLoading)
                                }
                */

                awaitItem().run {
                    assertFalse(isLoading)
                    assertFalse(silentLoading)
                    assertTrue(journeyList.isEmpty())
                    assertTrue(isError)
                }

                cancelAndConsumeRemainingEvents()
            }
        }

    // endregion

    // region Test for updateTripsCache

    @Test
    fun `GIVEN journeys returned from Trip api WHEN updateTripsCache is called THEN journeys object in ViewModel should be updated`() =
        runTest {
            // GIVEN Trip Response
            val tripResponse = buildTripResponse(
                numberOfJourney = 2,
                reverseTimeOrder = false,
            )
            viewModel.journeys.clear()
            tripResponse.journeys?.forEachIndexed { index, item ->
                println("tripResponse Journey #$index: ${item.legs?.get(0)?.origin?.arrivalTimeEstimated?.formatTo12HourTime()}")
            }

            // WHEN
            viewModel.updateTripsCache(tripResponse)

            // THEN
            val viewmodelJourneysList = viewModel.journeys.values.toList()
            assertEquals(2, viewmodelJourneysList.size)
        }

    @Test
    fun `GIVEN started journeys in cache are more than threshold WHEN updateTripsCache is called THEN extra started journeys should be removed from viewmodel`() =
        runTest {
            // GIVEN Trip Response
            val tripResponse = buildTripResponse(
                numberOfJourney = 2,
                reverseTimeOrder = false,
            )
            viewModel.journeys.putAll(
                buildStartedJourneysList(numberOfStartedJourneys = 5)
            )
            tripResponse.journeys?.forEachIndexed { index, item ->
                println("tripResponse Journey #$index: ${item.legs?.get(0)?.origin?.arrivalTimeEstimated?.formatTo12HourTime()}")
            }

            // WHEN
            viewModel.updateTripsCache(tripResponse)

            // THEN
            val viewmodelJourneysList = viewModel.journeys.values.toList()
            // 4 because 2 are from API response and 2 is threshold of started journeys
            assertEquals(4, viewmodelJourneysList.size)
        }

    @Test
    fun `GIVEN multiple started journeys in cache WHEN updateTripsCache is called THEN journeys should be sorted and updated`() =
        runTest {
            // GIVEN Trip Response
            val tripResponse = TripResponse()
            viewModel.journeys.putAll(
                buildStartedJourneysList(numberOfStartedJourneys = 4, distortSortOrder = true)
            )
            tripResponse.journeys?.forEachIndexed { index, item ->
                println("tripResponse Journey #$index: ${item.legs?.get(0)?.origin?.arrivalTimeEstimated?.formatTo12HourTime()}")
            }

            // WHEN
            viewModel.updateTripsCache(tripResponse)

            // THEN
            val viewmodelJourneysList = viewModel.journeys.values.toList()
            assertEquals(2, viewmodelJourneysList.size)
            // Check if the journeys are sorted by originUtcDateTime
            assertTrue(viewmodelJourneysList[0].originUtcDateTime < viewmodelJourneysList[1].originUtcDateTime)
        }

    // endregion

    /**
     * Builds a list of started journeys, i.e. journeys that have origin time in past.
     *
     * @param numberOfStartedJourneys The number of started journeys to create.
     *
     * @param distortSortOrder If true, the order of the journeys will be shuffled,means time will no longer be in ascending or descending.
     *
     * @return A map of journey IDs to JourneyCardInfo objects.
     */
    private fun buildStartedJourneysList(
        numberOfStartedJourneys: Int,
        distortSortOrder: Boolean = false,
    ): Map<String, TimeTableState.JourneyCardInfo> {
        val startedJourneys = mutableMapOf<String, TimeTableState.JourneyCardInfo>()
        for (i in 1..numberOfStartedJourneys) {

            // Calculate the origin time for each journey, decreasing by 5 minutes for each subsequent journey
            val originTime = Clock.System.now().minus(5.minutes * i)

            startedJourneys["startedJourney$i"] = TimeTableState.JourneyCardInfo(
                originUtcDateTime = originTime.toString(),
                destinationUtcDateTime = originTime.plus(1.hours).toString(),
                timeText = "1",
                platformText = "1",
                platformNumber = "1",
                originTime = "",
                destinationTime = "",
                travelTime = "",
                totalWalkTime = "",
                transportModeLines = persistentListOf(),
                legs = persistentListOf(
                    TimeTableState.JourneyCardInfo.Leg.TransportLeg(
                        transportModeLine = TransportModeLine(
                            transportMode = TransportMode.Train(),
                            lineName = "T1",
                        ),
                        displayText = "A via B",
                        totalDuration = "1 hour",
                        stops = persistentListOf(
                            Stop(name = "", time = "", isWheelchairAccessible = true),
                        ),
                        tripId = "id_$i",
                    )

                ),
                totalUniqueServiceAlerts = 1,
            )
        }

        // If distortSortOrder is true, shuffle the list of journeys before returning
        return if (distortSortOrder) {
            startedJourneys.toList().shuffled().toMap()
        } else startedJourneys
    }
}
