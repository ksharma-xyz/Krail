package xyz.ksharma.krail.trip.planner.ui.alerts

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.collections.immutable.toImmutableSet
import timber.log.Timber
import xyz.ksharma.krail.trip.planner.ui.navigation.ServiceAlertRoute
import xyz.ksharma.krail.trip.planner.ui.state.alerts.ServiceAlert

internal fun NavGraphBuilder.alertsDestination() {
    composable<ServiceAlertRoute> { backStackEntry ->

        val route = backStackEntry.toRoute<ServiceAlertRoute>()
        val serviceAlerts = route.alertsJsonList.mapNotNull { alertJson ->
            ServiceAlert.fromJsonString(alertJson)
        }.toImmutableSet()

        LaunchedEffect(route.alertsJsonList) {
            route.alertsJsonList.forEach {
                ServiceAlert.fromJsonString(it)?.let { alert ->
         //           Timber.d("Alert Heading: ${alert.heading}")
                    Timber.d("Alert Message: ${alert.message}")
                }
            }
        }

        ServiceAlertScreen(serviceAlerts = serviceAlerts)
    }
}
