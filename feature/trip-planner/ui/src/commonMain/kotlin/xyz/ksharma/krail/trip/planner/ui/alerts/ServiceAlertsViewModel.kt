package xyz.ksharma.krail.trip.planner.ui.alerts

import androidx.lifecycle.ViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.sandook.SelectServiceAlertsByJourneyId
import xyz.ksharma.krail.trip.planner.ui.state.alerts.ServiceAlert
import xyz.ksharma.krail.trip.planner.ui.state.alerts.ServiceAlertState

class ServiceAlertsViewModel(
    private val sandook: Sandook,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ServiceAlertState> =
        MutableStateFlow(ServiceAlertState())
    val uiState: StateFlow<ServiceAlertState> = _uiState

    fun fetchAlerts(journeyId: String) {
        // get alerts here and save to ui state.
        val alerts: List<ServiceAlert>? =
            sandook.selectServiceAlertById(journeyId)?.map { it.toServiceAlert() }
        updateUiState { copy(serviceAlerts = alerts?.toImmutableList() ?: persistentListOf()) }
    }

    private inline fun updateUiState(block: ServiceAlertState.() -> ServiceAlertState) {
        _uiState.update(block)
    }
}

private fun SelectServiceAlertsByJourneyId.toServiceAlert() =
    ServiceAlert(heading = heading, message = message)
