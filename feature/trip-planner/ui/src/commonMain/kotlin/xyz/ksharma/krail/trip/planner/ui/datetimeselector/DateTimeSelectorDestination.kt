package xyz.ksharma.krail.trip.planner.ui.datetimeselector

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import xyz.ksharma.krail.trip.planner.ui.navigation.DateTimeSelectorRoute

internal fun NavGraphBuilder.dateTimeSelectorDestination(navController: NavHostController) {
    composable<DateTimeSelectorRoute> { backStackEntry ->
        val viewModel: DateTimeSelectorViewModel = koinViewModel<DateTimeSelectorViewModel>()

        DateTimeSelectorScreen()
    }
}
