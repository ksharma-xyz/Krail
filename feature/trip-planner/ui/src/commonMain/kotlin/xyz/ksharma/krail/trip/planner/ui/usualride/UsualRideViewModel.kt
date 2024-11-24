package xyz.ksharma.krail.trip.planner.ui.usualride

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.trip.planner.ui.state.usualride.UsualRideEvent
import xyz.ksharma.krail.trip.planner.ui.state.usualride.UsualRideState

class UsualRideViewModel(private val sandook: Sandook) : ViewModel() {

    private val _uiState: MutableStateFlow<UsualRideState> = MutableStateFlow(UsualRideState())
    val uiState: StateFlow<UsualRideState> = _uiState

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
}
