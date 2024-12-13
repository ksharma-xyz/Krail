package xyz.ksharma.krail.trip.planner.ui.themeselection

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.collections.immutable.toImmutableSet
import org.koin.compose.viewmodel.koinViewModel
import xyz.ksharma.krail.taj.LocalThemeColor
import xyz.ksharma.krail.taj.LocalThemeContentColor
import xyz.ksharma.krail.taj.theme.getForegroundColor
import xyz.ksharma.krail.trip.planner.ui.components.hexToComposeColor
import xyz.ksharma.krail.trip.planner.ui.components.toHex
import xyz.ksharma.krail.trip.planner.ui.navigation.SavedTripsRoute
import xyz.ksharma.krail.trip.planner.ui.navigation.ThemeSelectionRoute
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.TransportModeSortOrder
import xyz.ksharma.krail.trip.planner.ui.state.usualride.ThemeSelectionEvent

internal fun NavGraphBuilder.themeSelectionDestination(navController: NavHostController) {
    composable<ThemeSelectionRoute> {
        val viewModel: ThemeSelectionViewModel = koinViewModel<ThemeSelectionViewModel>()
        val isUiStateLoading by viewModel.isLoading.collectAsStateWithLifecycle()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        var themeColor by LocalThemeColor.current
        var themeContentColor by LocalThemeContentColor.current
        var mode: TransportMode? by remember(state.selectedTransportMode) {
            mutableStateOf(state.selectedTransportMode)
        }
        themeContentColor =
            getForegroundColor(backgroundColor = themeColor.hexToComposeColor()).toHex()

        LaunchedEffect(state.selectedTransportMode) {
            println("selectedTransportMode: ${state.selectedTransportMode}")
        }
        LaunchedEffect(state.themeSelected) {
            if(state.themeSelected) {
                navController.navigate(
                    route = SavedTripsRoute,
                    navOptions = NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setPopUpTo<SavedTripsRoute>(inclusive = false)
                        .build(),
                )
            }
        }

        ThemeSelectionScreen(
            selectedTransportMode = state.selectedTransportMode,
            transportModes = TransportMode.sortedValues(TransportModeSortOrder.PRODUCT_CLASS)
                .toImmutableSet(),
            transportModeSelected = { productClass ->
                mode = TransportMode.toTransportModeType(productClass)
                check(mode != null) {
                    "Transport mode not found for product class $productClass"
                }
                viewModel.onEvent(ThemeSelectionEvent.TransportModeSelected(productClass))
                mode?.colorCode?.let { themeColor = it }
            },
            onBackClick = { navController.popBackStack() }
        )
    }
}
