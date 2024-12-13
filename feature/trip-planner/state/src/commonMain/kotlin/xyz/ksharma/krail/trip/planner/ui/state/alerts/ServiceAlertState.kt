package xyz.ksharma.krail.trip.planner.ui.state.alerts

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ServiceAlertState(
    val serviceAlerts: ImmutableList<ServiceAlert> = persistentListOf(),
)
