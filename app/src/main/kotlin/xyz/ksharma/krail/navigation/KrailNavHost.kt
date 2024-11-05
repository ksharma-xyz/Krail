package xyz.ksharma.krail.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import xyz.ksharma.krail.design.system.LocalThemeColor
import xyz.ksharma.krail.design.system.LocalThemeContentColor
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.design.system.unspecifiedColor
import xyz.ksharma.krail.splash.SplashScreen
import xyz.ksharma.krail.splash.SplashViewModel
import xyz.ksharma.krail.trip.planner.ui.components.toHex
import xyz.ksharma.krail.trip.planner.ui.navigation.SavedTripsRoute
import xyz.ksharma.krail.trip.planner.ui.navigation.UsualRideRoute
import xyz.ksharma.krail.trip.planner.ui.navigation.tripPlannerDestinations
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode

/**
 * TODO - I don't like [NavHost] defined in app module, I would love to refactor it to :core:navigation module
 *    but that results in a cyclic dependency. Feature module needs to depend on :core:navigation for navigation logic and
 *    then core:navigation needs to depend on feature module for the destinations / nested navigation graphs.
 *    This results in putting all nav logic in the app module, which will have negative impacts on the build time.
 *    Why is navigation so hard in Compose?
 *
 *   Navigation logic is currently taken from [NowInAndroid](https://github.com/android/nowinandroid] app,
 *   so fine for now. But I will want to refactor it to something nicer e.g. using Circuit library
 *   from Slack, but that would also mean refactoring to use MVP instead of MVVM.
 */
@Composable
fun KrailNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val themeColorHexCode = rememberSaveable { mutableStateOf(unspecifiedColor) }
    var productClass: Int? by rememberSaveable { mutableStateOf(null) }
    val themeContentColorHexCode = rememberSaveable { mutableStateOf(unspecifiedColor) }

    themeContentColorHexCode.value =
        when (productClass?.let { TransportMode.toTransportModeType(it) }) {
            is TransportMode.Bus -> KrailTheme.colors.onBusTheme
            is TransportMode.Coach -> KrailTheme.colors.onCoachTheme
            is TransportMode.Ferry -> KrailTheme.colors.onFerryTheme
            is TransportMode.LightRail -> KrailTheme.colors.onLightRailTheme
            is TransportMode.Metro -> KrailTheme.colors.onMetroTheme
            is TransportMode.Train -> KrailTheme.colors.onTrainTheme
            else -> KrailTheme.colors.onSurface
        }.toHex()

    CompositionLocalProvider(
        LocalThemeColor provides themeColorHexCode,
        LocalThemeContentColor provides themeContentColorHexCode,
    ) {
        NavHost(
            navController = navController,
            startDestination = SplashScreen,
            modifier = modifier.fillMaxSize(),
        ) {
            tripPlannerDestinations(navController = navController)

            composable<SplashScreen> {
                val viewModel = hiltViewModel<SplashViewModel>()
                val mode = viewModel.getThemeTransportMode()
                productClass = mode?.productClass
                themeColorHexCode.value = mode?.colorCode ?: unspecifiedColor

                SplashScreen(
                    onSplashComplete = {
                        navController.navigate(
                            route = if (productClass != null) {
                                SavedTripsRoute
                            } else {
                                UsualRideRoute
                            },
                            navOptions = NavOptions.Builder()
                                .setLaunchSingleTop(true)
                                .setPopUpTo<SplashScreen>(inclusive = true)
                                .build(),
                        )
                    },
                )
            }
        }
    }
}

@Serializable
private data object SplashScreen
