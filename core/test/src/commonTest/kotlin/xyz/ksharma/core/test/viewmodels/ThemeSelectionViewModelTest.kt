package xyz.ksharma.core.test.viewmodels

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import xyz.ksharma.core.test.fakes.FakeAnalytics
import xyz.ksharma.core.test.fakes.FakeSandook
import xyz.ksharma.krail.core.analytics.Analytics
import xyz.ksharma.krail.trip.planner.ui.state.usualride.ThemeSelectionEvent
import xyz.ksharma.krail.trip.planner.ui.themeselection.ThemeSelectionViewModel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ThemeSelectionViewModelTest {

    private val fakeAnalytics: Analytics = FakeAnalytics()
    private val fakeSandook = FakeSandook()
    private lateinit var viewModel: ThemeSelectionViewModel

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ThemeSelectionViewModel(
            sandook = fakeSandook,
            analytics = fakeAnalytics,
            ioDispatcher = testDispatcher
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN ThemeSelectionViewModel initial state WHEN uiState is collected THEN assert Initial State`() =
        runTest {
            viewModel.uiState.test {
                awaitItem().run {
                    assertNull(selectedTransportMode)
                    assertFalse(themeSelected)
                }

                cancelAndIgnoreRemainingEvents()
            }
        }


    // TODO - Write UT - `GIVEN ThemeSelectionViewModel WHEN isLoading is collected THEN analytics event is tracked`()
    // TODO - write Sandook tests

    @Test
    fun `GIVEN transport mode WHEN TransportModeSelected is triggered THEN uiState is updated with themeSelected as true`() =
        runTest {
            viewModel.uiState.test {
                awaitItem().run {
                    assertNull(selectedTransportMode)
                    assertFalse(themeSelected)
                    println(this)

                }

                viewModel.onEvent(ThemeSelectionEvent.TransportModeSelected(1))

                awaitItem().run {
                    println(this)
                   assertTrue(themeSelected)
                }

                cancelAndIgnoreRemainingEvents()
            }
        }
}
