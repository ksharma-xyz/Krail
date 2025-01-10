package xyz.ksharma.krail.trip.planner.ui.themeselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.ksharma.krail.core.analytics.Analytics
import xyz.ksharma.krail.core.analytics.AnalyticsScreen
import xyz.ksharma.krail.core.analytics.event.trackScreenViewEvent
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.usualride.ThemeSelectionEvent
import xyz.ksharma.krail.trip.planner.ui.state.usualride.ThemeSelectionState

class ThemeSelectionViewModel(
    private val sandook: Sandook,
    private val analytics: Analytics,
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ThemeSelectionState> =
        MutableStateFlow(ThemeSelectionState())
    val uiState: StateFlow<ThemeSelectionState> = _uiState
        .onStart {
            getThemeTransportMode()
            analytics.trackScreenViewEvent(screen = AnalyticsScreen.ThemeSelection)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(ANR_TIMEOUT), ThemeSelectionState())

    private var transportSelectionJob: Job? = null

    fun onEvent(event: ThemeSelectionEvent) {
        when (event) {
            is ThemeSelectionEvent.TransportModeSelected -> onTransportModeSelected(event.productClass)
        }
    }

    private fun onTransportModeSelected(productClass: Int) {
        transportSelectionJob?.cancel()
        transportSelectionJob = viewModelScope.launch(ioDispatcher) {
            sandook.clearTheme() // Only one entry should exist at a time
            sandook.insertOrReplaceTheme(productClass.toLong())
            updateUiState {
                copy(themeSelected = true)
            }
            analytics.trackThemeSelectionEvent(productClass)
        }
    }

    private fun getThemeTransportMode() {
        viewModelScope.launch(ioDispatcher) {
            // First app launch there will be no product class, so use default transport mode theme.
            val productClass = sandook.getProductClass()?.toInt()
            val mode =
                productClass?.let { TransportMode.toTransportModeType(it) } ?: TransportMode.Train()
            updateUiState {
                copy(selectedTransportMode = mode)
            }
        }
    }

    private inline fun updateUiState(block: ThemeSelectionState.() -> ThemeSelectionState) {
        _uiState.update(block)
    }

    companion object {
        const val ANR_TIMEOUT = 5000L
    }
}
