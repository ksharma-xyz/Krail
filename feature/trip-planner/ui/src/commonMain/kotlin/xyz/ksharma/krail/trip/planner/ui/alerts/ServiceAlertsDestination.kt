package xyz.ksharma.krail.trip.planner.ui.alerts

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.collections.immutable.toImmutableList
import org.koin.compose.viewmodel.koinViewModel
import xyz.ksharma.krail.trip.planner.ui.navigation.ServiceAlertRoute

internal fun NavGraphBuilder.alertsDestination(navController: NavHostController) {
    composable<ServiceAlertRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<ServiceAlertRoute>()
        val viewModel: ServiceAlertsViewModel = koinViewModel<ServiceAlertsViewModel>()
        val serviceAlertState by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            viewModel.fetchAlerts(route.journeyId)
        }

        ServiceAlertScreen(
            serviceAlerts = serviceAlertState.serviceAlerts.toImmutableList(),
            onBackClick = {
                navController.popBackStack()
            },
        )
    }
}
