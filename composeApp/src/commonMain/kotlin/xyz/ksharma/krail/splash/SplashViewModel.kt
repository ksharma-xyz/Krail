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
import xyz.ksharma.krail.core.analytics.Analytics
import xyz.ksharma.krail.core.analytics.event.AnalyticsEvent
import xyz.ksharma.krail.core.appinfo.AppInfoProvider
import xyz.ksharma.krail.core.log.log
import xyz.ksharma.krail.core.remote_config.RemoteConfig
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.taj.theme.DEFAULT_THEME_STYLE
import xyz.ksharma.krail.taj.theme.KrailThemeStyle

class SplashViewModel(
    private val sandook: Sandook,
    private val analytics: Analytics,
    private val appInfoProvider: AppInfoProvider,
    private val remoteConfig: RemoteConfig,
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState: MutableStateFlow<KrailThemeStyle> =
        MutableStateFlow(DEFAULT_THEME_STYLE)
    val uiState: MutableStateFlow<KrailThemeStyle> = _uiState

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
        .onStart {
            loadKrailThemeStyle()
            trackAppStartEvent()
            remoteConfig.setup() // App Start Event
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    private fun trackAppStartEvent() = with(appInfoProvider.getAppInfo()) {
        log("AppInfo: $this")
        analytics.track(
            AnalyticsEvent.AppStart(
                platformType = devicePlatformType.name,
                osVersion = osVersion,
                appVersion = appVersion,
                fontSize = fontSize,
                isDarkTheme = isDarkTheme,
                deviceModel = deviceModel,
                krailTheme = _uiState.value.id,
                locale = locale,
                batteryLevel = batteryLevel,
                timeZone = timeZone
            )
        )
    }

    private fun loadKrailThemeStyle() {
        viewModelScope.launch(ioDispatcher) {
            // First app launch there will be no product class, so use default transport mode theme.
            val themeId =
                sandook.getProductClass()?.toInt() ?: DEFAULT_THEME_STYLE.id
            val themeColor = KrailThemeStyle.entries.find { it.id == themeId }
            _uiState.value = themeColor ?: DEFAULT_THEME_STYLE
        }
    }
}
