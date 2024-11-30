package xyz.ksharma.krail.trip.planner.ui.datetimeselector

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.koin.compose.viewmodel.koinViewModel
import xyz.ksharma.krail.trip.planner.ui.navigation.DateTimeSelectorRoute

internal fun NavGraphBuilder.dateTimeSelectorDestination(navController: NavHostController) {
    composable<DateTimeSelectorRoute> { backStackEntry ->
        val viewModel: DateTimeSelectorViewModel = koinViewModel<DateTimeSelectorViewModel>()

        // TODO - If arguments have data, then display that data in the Screen else display current date time.

        DateTimeSelectorScreen(
            onBackClick = {
                navController.popBackStack()
            },
            onDateTimeSelected = { dateTimeSelection ->
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    key = DateTimeSelectorRoute.DATE_TIME_TEXT_KEY,
                    value = dateTimeSelection?.toJsonString(),
                )
                navController.popBackStack()
            }
        )
    }
}
