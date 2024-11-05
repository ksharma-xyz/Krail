package xyz.ksharma.krail.trip.planner.ui.usualride

import android.window.SplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.collections.immutable.toImmutableSet
import xyz.ksharma.krail.trip.planner.ui.navigation.SavedTripsRoute
import xyz.ksharma.krail.trip.planner.ui.navigation.UsualRideRoute
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.TransportModeSortOrder
import xyz.ksharma.krail.trip.planner.ui.state.usualride.UsualRideEvent

internal fun NavGraphBuilder.usualRideDestination(navController: NavHostController) {
    composable<UsualRideRoute> {
        val viewModel = hiltViewModel<UsualRideViewModel>()

        UsualRideScreen(
            transportModes = TransportMode.sortedValues(TransportModeSortOrder.PRODUCT_CLASS)
                .toImmutableSet(),
            transportModeSelected = { productClass ->
                viewModel.onEvent(UsualRideEvent.TransportModeSelected(productClass))
                navController.navigate(
                    route = SavedTripsRoute,
                    navOptions = NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setPopUpTo<SplashScreen>(inclusive = true)
                        .build(),
                )
            },
        )
    }
}
