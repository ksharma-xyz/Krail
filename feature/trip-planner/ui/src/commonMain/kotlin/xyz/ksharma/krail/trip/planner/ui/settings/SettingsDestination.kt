package xyz.ksharma.krail.trip.planner.ui.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import xyz.ksharma.krail.trip.planner.ui.navigation.SettingsRoute
import xyz.ksharma.krail.trip.planner.ui.navigation.UsualRideRoute

internal fun NavGraphBuilder.settingsDestination(navController: NavHostController) {
    composable<SettingsRoute> {
        SettingsScreen(
            onChangeThemeClick = {
                navController.navigate(
                    route = UsualRideRoute,
                    navOptions = NavOptions.Builder().setLaunchSingleTop(true).build(),
                )
            },
            onBackClick = {
                navController.popBackStack()
            },
        )
    }
}
