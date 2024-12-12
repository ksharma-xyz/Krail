package xyz.ksharma.krail.trip.planner.ui.alerts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import xyz.ksharma.krail.trip.planner.ui.alerts.cache.ServiceAlertsCache
import xyz.ksharma.krail.trip.planner.ui.state.alerts.ServiceAlertState

class ServiceAlertsViewModel(
    private val alertsCache: ServiceAlertsCache,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ServiceAlertState> =
        MutableStateFlow(ServiceAlertState())
    val uiState: StateFlow<ServiceAlertState> = _uiState
        .onStart {
            _uiState.value = ServiceAlertState(alertsCache.getAlerts("journeyId").toImmutableList())
        }
        .stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), ServiceAlertState()
        )
}
