package xyz.ksharma.krail.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import xyz.ksharma.krail.DEFAULT_THEME_COLOR
import xyz.ksharma.krail.core.analytics.Analytics
import xyz.ksharma.krail.core.analytics.event.AnalyticsEvent
import xyz.ksharma.krail.core.appinfo.AppInfoProvider
import xyz.ksharma.krail.core.remote_config.RemoteConfig
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.taj.theme.ThemeColor
import xyz.ksharma.krail.taj.theme.getThemeColors
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode

class SplashViewModel(
    private val sandook: Sandook,
    private val analytics: Analytics,
    private val appInfoProvider: AppInfoProvider,
    private val remoteConfig: RemoteConfig,
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ThemeColor> =
        MutableStateFlow(DEFAULT_THEME_COLOR)
    val uiState: MutableStateFlow<ThemeColor> = _uiState

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
        .onStart {
            getThemeTransportMode()
            trackAppStartEvent()
            remoteConfig.setup() // App Start Event
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    private fun trackAppStartEvent() = with(appInfoProvider.getAppInfo()) {
        analytics.track(
            AnalyticsEvent.AppStart(
                deviceType = devicePlatformType.name,
                osVersion = osVersion,
                appVersion = appVersion,
                fontSize = fontSize,
                isDarkTheme = isDarkTheme,
                deviceModel = deviceModel,
            )
        )
    }

    private fun getThemeTransportMode() {
        viewModelScope.launch(ioDispatcher) {
            // First app launch there will be no product class, so use default transport mode theme.
            val themeId =
                sandook.getProductClass()?.toInt() ?: DEFAULT_THEME_COLOR.id
            val themeColor = getThemeColors().find { it.id == themeId }
            _uiState.value = themeColor ?: DEFAULT_THEME_COLOR
        }
    }
}
