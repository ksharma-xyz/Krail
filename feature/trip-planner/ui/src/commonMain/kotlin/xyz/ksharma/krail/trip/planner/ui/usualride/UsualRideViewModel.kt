package xyz.ksharma.krail.trip.planner.ui.usualride

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import xyz.ksharma.krail.sandook.RealSandook
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.usualride.UsualRideEvent
import xyz.ksharma.krail.trip.planner.ui.state.usualride.UsualRideState

class UsualRideViewModel : ViewModel() {

    private val sandook: Sandook = RealSandook()

    private val _uiState: MutableStateFlow<UsualRideState> = MutableStateFlow(UsualRideState())
    val uiState: StateFlow<UsualRideState> = _uiState

    fun onEvent(event: UsualRideEvent) {
        when (event) {
            is UsualRideEvent.TransportModeSelected -> onTransportModeSelected(event.productClass)
        }
    }

    private fun onTransportModeSelected(productClass: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            TransportMode.toTransportModeType(productClass)?.let { mode ->
                //Timber.d("onTransportModeSelected: $mode")
                sandook.putString("selectedMode", mode.productClass.toString())
            }
        }
    }
}
