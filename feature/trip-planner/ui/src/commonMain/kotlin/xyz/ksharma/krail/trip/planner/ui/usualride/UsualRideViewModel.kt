package xyz.ksharma.krail.trip.planner.ui.usualride

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.usualride.UsualRideEvent
import xyz.ksharma.krail.trip.planner.ui.state.usualride.UsualRideState

class UsualRideViewModel(private val sandook: Sandook) : ViewModel() {

    private val _uiState: MutableStateFlow<UsualRideState> = MutableStateFlow(UsualRideState())
    val uiState: StateFlow<UsualRideState> = _uiState

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
        .onStart { getThemeTransportMode() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(ANR_TIMEOUT), true)

    fun onEvent(event: UsualRideEvent) {
        when (event) {
            is UsualRideEvent.TransportModeSelected -> onTransportModeSelected(event.productClass)
        }
    }

   private fun onTransportModeSelected(productClass: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            sandook.clearTheme() // Only one entry should exist at a time
            sandook.insertOrReplaceTheme(productClass.toLong())
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

    private inline fun updateUiState(block: UsualRideState.() -> UsualRideState) {
        _uiState.update(block)
    }

    companion object {
        const val ANR_TIMEOUT = 5000L
    }
}
