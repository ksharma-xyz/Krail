package xyz.ksharma.core.test.viewmodels

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import xyz.ksharma.core.test.fakes.FakeAnalytics
import xyz.ksharma.core.test.fakes.FakeSandook
import xyz.ksharma.krail.core.analytics.Analytics
import xyz.ksharma.krail.core.analytics.AnalyticsScreen
import xyz.ksharma.krail.core.analytics.event.trackScreenViewEvent
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.trip.planner.ui.savedtrips.SavedTripsViewModel
import xyz.ksharma.krail.trip.planner.ui.state.savedtrip.SavedTripsState
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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
            println("viewModel.uiState.test")
            val item = awaitItem()
            assertEquals(item, SavedTripsState())

            delay(1) // Required for onStart to be called. - TODO is there a better way?
            assertTrue(analytics.wasScreenViewEventTracked("view_screen"))

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `analytics event should not be tracked when no observer is active`() = runTest {
        assertFalse((analytics as FakeAnalytics).wasScreenViewEventTracked("view_screen"))
    }
}
