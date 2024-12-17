package xyz.ksharma.krail.trip.planner.ui.alerts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import xyz.ksharma.krail.core.analytics.Analytics
import xyz.ksharma.krail.core.analytics.AnalyticsScreen
import xyz.ksharma.krail.core.analytics.event.trackScreenViewEvent
import xyz.ksharma.krail.trip.planner.ui.alerts.cache.ServiceAlertsCache
import xyz.ksharma.krail.trip.planner.ui.state.alerts.ServiceAlert
import xyz.ksharma.krail.trip.planner.ui.state.alerts.ServiceAlertState
import xyz.ksharma.krail.trip.planner.ui.state.settings.SettingsState

class ServiceAlertsViewModel(
    private val alertsCache: ServiceAlertsCache,
    private val analytics: Analytics,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ServiceAlertState> =
        MutableStateFlow(ServiceAlertState())
    val uiState: StateFlow<ServiceAlertState> = _uiState
        .onStart {
            analytics.trackScreenViewEvent(screen = AnalyticsScreen.ServiceAlerts)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ServiceAlertState())

    fun fetchAlerts(journeyId: String): List<ServiceAlert>? {
        // get alerts here and save to ui state.
        val alerts = alertsCache.getAlerts(journeyId)
        return alerts
    }

    override fun onCleared() {
        super.onCleared()
    }
}
