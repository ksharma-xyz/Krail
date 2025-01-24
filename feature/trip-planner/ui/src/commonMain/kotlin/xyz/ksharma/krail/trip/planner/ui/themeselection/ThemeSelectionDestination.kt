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
import kotlinx.collections.immutable.toImmutableList
import org.koin.compose.viewmodel.koinViewModel
import xyz.ksharma.krail.core.log.log
import xyz.ksharma.krail.taj.LocalThemeColor
import xyz.ksharma.krail.taj.LocalThemeContentColor
import xyz.ksharma.krail.taj.hexToComposeColor
import xyz.ksharma.krail.taj.theme.ThemeColor
import xyz.ksharma.krail.taj.theme.getForegroundColor
import xyz.ksharma.krail.taj.theme.getThemeColors
import xyz.ksharma.krail.taj.toHex
import xyz.ksharma.krail.trip.planner.ui.navigation.SavedTripsRoute
import xyz.ksharma.krail.trip.planner.ui.navigation.ThemeSelectionRoute
import xyz.ksharma.krail.trip.planner.ui.state.usualride.ThemeSelectionEvent

internal fun NavGraphBuilder.themeSelectionDestination(navController: NavHostController) {
    composable<ThemeSelectionRoute> {
        val viewModel: ThemeSelectionViewModel = koinViewModel<ThemeSelectionViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        var themeColor by LocalThemeColor.current
        var themeContentColor by LocalThemeContentColor.current
        /*var interimSelectedThemeColor: ThemeColor? by remember(state.selectedThemeColor) {
            mutableStateOf(state.selectedThemeColor)
        }*/
        themeContentColor =
            getForegroundColor(backgroundColor = themeColor.hexToComposeColor()).toHex()

        LaunchedEffect(state.selectedThemeColor) {
            log("selectedTransportMode: ${state.selectedThemeColor}")
        }
        LaunchedEffect(state.themeSelected) {
            if (state.themeSelected) {
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
            selectedThemeColor = state.selectedThemeColor,
            themeColors = getThemeColors().toImmutableList(),
            onThemeSelected = { themeId ->
                val hexColorCode = getThemeColors().firstOrNull { it.id == themeId }?.hexColorCode
                check(hexColorCode != null) { "hexColorCode for themeId $themeId not found" }

                // This will change the theme color by updating CompositionLocal
                themeColor = hexColorCode
                // Save the selected theme color to db.
                viewModel.onEvent(ThemeSelectionEvent.ThemeSelected(themeId))
            },
            onBackClick = { navController.popBackStack() }
        )
    }
}
