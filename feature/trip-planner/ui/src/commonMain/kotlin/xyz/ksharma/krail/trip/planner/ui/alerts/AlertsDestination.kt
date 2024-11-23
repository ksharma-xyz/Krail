package xyz.ksharma.krail.trip.planner.ui.alerts

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.collections.immutable.toImmutableSet
import xyz.ksharma.krail.trip.planner.ui.navigation.ServiceAlertRoute
import xyz.ksharma.krail.trip.planner.ui.state.alerts.ServiceAlert

internal fun NavGraphBuilder.alertsDestination(navController: NavHostController) {
    composable<ServiceAlertRoute> { backStackEntry ->

        val route = backStackEntry.toRoute<ServiceAlertRoute>()
        val serviceAlerts = route.alertsJsonList.mapNotNull { alertJson ->
            ServiceAlert.fromJsonString(alertJson)
        }.toImmutableSet()

        ServiceAlertScreen(serviceAlerts = serviceAlerts, onBackClick = {
            navController.popBackStack()
        })
    }
}
