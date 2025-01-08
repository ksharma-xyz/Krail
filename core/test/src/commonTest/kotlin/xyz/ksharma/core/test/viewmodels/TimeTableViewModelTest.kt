package xyz.ksharma.core.test.viewmodels

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import xyz.ksharma.core.test.fakes.FakeAnalytics
import xyz.ksharma.core.test.fakes.FakeRateLimiter
import xyz.ksharma.core.test.fakes.FakeSandook
import xyz.ksharma.core.test.fakes.FakeTripPlanningService
import xyz.ksharma.krail.core.analytics.Analytics
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.trip.planner.ui.state.timetable.TimeTableUiEvent
import xyz.ksharma.krail.trip.planner.ui.state.timetable.Trip
import xyz.ksharma.krail.trip.planner.ui.timetable.TimeTableViewModel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

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

}
