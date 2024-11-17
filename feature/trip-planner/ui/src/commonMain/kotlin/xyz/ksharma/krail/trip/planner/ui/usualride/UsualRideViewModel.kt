package xyz.ksharma.krail.trip.planner.ui.usualride

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.sandook.di.SandookFactory
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.usualride.UsualRideEvent
import xyz.ksharma.krail.trip.planner.ui.state.usualride.UsualRideState

class UsualRideViewModel(
    sandookFactory: SandookFactory,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val sandook: Sandook = sandookFactory.create(SandookFactory.SandookKey.THEME)

    private val _uiState: MutableStateFlow<UsualRideState> = MutableStateFlow(UsualRideState())
    val uiState: StateFlow<UsualRideState> = _uiState

    fun onEvent(event: UsualRideEvent) {
        when (event) {
            is UsualRideEvent.TransportModeSelected -> onTransportModeSelected(event.productClass)
        }
    }

    private fun onTransportModeSelected(productClass: Int) {
        viewModelScope.launch(ioDispatcher) {
            TransportMode.toTransportModeType(productClass)?.let { mode ->
                //Timber.d("onTransportModeSelected: $mode")
                sandook.putInt("selectedMode", mode.productClass)
            }
        }
    }
}
