package xyz.ksharma.krail.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.sandook.di.SandookFactory
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    sandookFactory: SandookFactory,
) : ViewModel() {

    private val sandook: Sandook = sandookFactory.create(SandookFactory.SandookKey.THEME)

    private val _uiState: MutableStateFlow<TransportMode?> = MutableStateFlow(null)
    val uiState: MutableStateFlow<TransportMode?> = _uiState

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
        .onStart {
            getThemeTransportMode()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    private fun getThemeTransportMode() {
        viewModelScope.launch(Dispatchers.IO) {
            val productClass = sandook.getInt("selectedMode")
            val mode = TransportMode.toTransportModeType(productClass)
            _uiState.value = mode
        }
    }
}
