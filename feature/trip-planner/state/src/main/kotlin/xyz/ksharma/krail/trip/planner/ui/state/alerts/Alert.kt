package xyz.ksharma.krail.trip.planner.ui.state.alerts

data class Alert(val heading: String, val message: String, val priority: ServiceAlertPriority)

enum class ServiceAlertPriority {
    HIGH, MEDIUM, LOW
}
