package xyz.ksharma.krail.trip.planner.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import xyz.ksharma.krail.trip.planner.ui.state.settings.SettingsState
import xyz.ksharma.krail.version.AppVersionProvider

class SettingsViewModel(private val appVersionProvider: AppVersionProvider) : ViewModel() {

    private val _uiState: MutableStateFlow<SettingsState> = MutableStateFlow(SettingsState())
    val uiState: StateFlow<SettingsState> = _uiState
        .onStart {
            fetchAppVersion()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SettingsState())

    private fun fetchAppVersion() {
        val appVersion = appVersionProvider.getAppVersion()
        _uiState.value = _uiState.value.copy(appVersion = appVersion)
    }
}
