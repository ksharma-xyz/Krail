package xyz.ksharma.core.test.viewmodels

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import xyz.ksharma.core.test.fakes.FakeAnalytics
import xyz.ksharma.core.test.fakes.FakeSandook
import xyz.ksharma.krail.core.analytics.Analytics
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.trip.planner.ui.savedtrips.SavedTripsViewModel
import xyz.ksharma.krail.trip.planner.ui.state.savedtrip.SavedTripUiEvent
import xyz.ksharma.krail.trip.planner.ui.state.savedtrip.SavedTripsState
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
class SavedTripsViewModelTest {

    private val sandook: Sandook = FakeSandook()
    private val analytics: Analytics = FakeAnalytics()
    private lateinit var viewModel: SavedTripsViewModel

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SavedTripsViewModel(sandook, analytics, testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `analytics event should be tracked when observer is active`() = runTest {
        // Ensure analytics events have not been tracked before observation
        assertFalse((analytics as FakeAnalytics).wasScreenViewEventTracked("view_screen"))

        viewModel.uiState.test {
            val item = awaitItem()
            assertEquals(item, SavedTripsState())

            advanceUntilIdle()
            assertTrue(analytics.wasScreenViewEventTracked("view_screen"))

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `analytics event should not be tracked when no observer is active`() = runTest {
        assertFalse((analytics as FakeAnalytics).wasScreenViewEventTracked("view_screen"))
    }

    @Test
    fun `GIVEN a saved trip WHEN LoadSavedTrips event is triggered THEN UiState should update savedTrips`() =
        runTest {

            // GIVEN a saved trip
            sandook.insertOrReplaceTrip(
                tripId = "1",
                fromStopId = "FROM_STOP_ID_1",
                fromStopName = "STOP_NAME_1",
                toStopId = "TO_ID_1",
                toStopName = "STOP_NAME_2",
            )

            // Ensure initial state
            viewModel.uiState.test {
                skipItems(1)

                // WHEN the LoadSavedTrips event is triggered
                viewModel.onEvent(SavedTripUiEvent.LoadSavedTrips)

                val item = awaitItem()

                // THEN Verify that the state is updated after loading trips
                assertFalse(item.isLoading)
                assertTrue(item.savedTrips.isNotEmpty())

                cancelAndIgnoreRemainingEvents()
            }
        }
}
