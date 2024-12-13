package xyz.ksharma.krail.trip.planner.ui.themeselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.usualride.ThemeSelectionEvent
import xyz.ksharma.krail.trip.planner.ui.state.usualride.ThemeSelectionState

class ThemeSelectionViewModel(private val sandook: Sandook) : ViewModel() {

    private val _uiState: MutableStateFlow<ThemeSelectionState> = MutableStateFlow(ThemeSelectionState())
    val uiState: StateFlow<ThemeSelectionState> = _uiState

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
        .onStart { getThemeTransportMode() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(ANR_TIMEOUT), true)

    private var transportSelectionJob: Job? = null

    fun onEvent(event: ThemeSelectionEvent) {
        when (event) {
            is ThemeSelectionEvent.TransportModeSelected -> onTransportModeSelected(event.productClass)
        }
    }

    private fun onTransportModeSelected(productClass: Int) {
        transportSelectionJob?.cancel()
        transportSelectionJob = viewModelScope.launch(Dispatchers.IO) {
            sandook.clearTheme() // Only one entry should exist at a time
            sandook.insertOrReplaceTheme(productClass.toLong())
            updateUiState {
                copy(themeSelected = true)
            }
        }
    }

    private fun getThemeTransportMode() {
        viewModelScope.launch(Dispatchers.IO) {
            // First app launch there will be no product class, so use default transport mode theme.
            val productClass = sandook.getProductClass()?.toInt()
            val mode = productClass?.let { TransportMode.toTransportModeType(it) }
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
