package xyz.ksharma.krail.trip.planner.ui.alerts

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import xyz.ksharma.krail.trip.planner.ui.alerts.cache.ServiceAlertsCache
import xyz.ksharma.krail.trip.planner.ui.state.alerts.ServiceAlert
import xyz.ksharma.krail.trip.planner.ui.state.alerts.ServiceAlertState

class ServiceAlertsViewModel(
    private val alertsCache: ServiceAlertsCache,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ServiceAlertState> =
        MutableStateFlow(ServiceAlertState())
    val uiState: StateFlow<ServiceAlertState> = _uiState

    fun fetchAlerts(journeyId: String): List<ServiceAlert>? {
        // get alerts here and save to ui state.
        val alerts = alertsCache.getAlerts(journeyId)
        return alerts
    }

    override fun onCleared() {
        super.onCleared()
    }
}
