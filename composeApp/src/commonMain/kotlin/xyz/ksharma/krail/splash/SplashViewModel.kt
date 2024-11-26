package xyz.ksharma.krail.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import xyz.ksharma.krail.DEFAULT_THEME_TRANSPORT_MODE
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode

class SplashViewModel(
    private val sandook: Sandook,
) : ViewModel() {

    private val _uiState: MutableStateFlow<TransportMode> = MutableStateFlow(DEFAULT_THEME_TRANSPORT_MODE)
    val uiState: MutableStateFlow<TransportMode> = _uiState

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
        .onStart {
            getThemeTransportMode()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    private fun getThemeTransportMode() {
        viewModelScope.launch(Dispatchers.IO) {
            // First app launch there will be no product class, so use default transport mode theme.
            val productClass =
                sandook.getProductClass()?.toInt() ?: DEFAULT_THEME_TRANSPORT_MODE.productClass
            val mode = TransportMode.toTransportModeType(productClass)
            _uiState.value = mode ?: DEFAULT_THEME_TRANSPORT_MODE
        }
    }
}
