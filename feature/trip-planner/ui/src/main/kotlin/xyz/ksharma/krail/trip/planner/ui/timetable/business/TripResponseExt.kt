package xyz.ksharma.krail.trip.planner.ui.timetable.business

import xyz.ksharma.krail.trip.planner.network.api.model.TripResponse
import xyz.ksharma.krail.trip.planner.ui.state.alerts.Alert
import xyz.ksharma.krail.trip.planner.ui.state.alerts.ServiceAlertPriority

fun TripResponse.Info.toAlert(): Alert? {
    val heading = subtitle
    val alertContent = content
    val alertPriority = priority.toServiceAlertPriority()

    return if (heading != null && alertContent != null && alertPriority != null) {
        Alert(
            heading = heading,
            message = alertContent,
            priority = alertPriority,
        )
    } else {
        null
    }
}

private fun String?.toServiceAlertPriority(): ServiceAlertPriority? {
    return when {
        equals("veryHigh", ignoreCase = true) ||
            equals("high", ignoreCase = true) -> ServiceAlertPriority.HIGH
        equals("normal", ignoreCase = true) -> ServiceAlertPriority.MEDIUM
        equals("low", ignoreCase = true) ||
            equals("veryLow", ignoreCase = true) -> ServiceAlertPriority.LOW
        else -> null
    }
}
