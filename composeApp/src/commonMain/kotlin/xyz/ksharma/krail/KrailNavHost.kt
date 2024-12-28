package xyz.ksharma.krail

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import xyz.ksharma.krail.splash.SplashScreen
import xyz.ksharma.krail.splash.SplashViewModel
import xyz.ksharma.krail.taj.LocalNavAnimatedVisibilityScope
import xyz.ksharma.krail.taj.LocalSharedTransitionScope
import xyz.ksharma.krail.taj.LocalThemeColor
import xyz.ksharma.krail.taj.LocalThemeContentColor
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.taj.theme.getForegroundColor
import xyz.ksharma.krail.taj.unspecifiedColor
import xyz.ksharma.krail.trip.planner.ui.components.hexToComposeColor
import xyz.ksharma.krail.trip.planner.ui.components.toHex
import xyz.ksharma.krail.trip.planner.ui.navigation.SavedTripsRoute
import xyz.ksharma.krail.trip.planner.ui.navigation.animComposable
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
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun KrailNavHost() {
    SharedTransitionLayout {
        val navController = rememberNavController()
        val themeColorHexCode = rememberSaveable { mutableStateOf(unspecifiedColor) }
        var productClass: Int? by rememberSaveable { mutableStateOf(null) }
        val themeContentColorHexCode = rememberSaveable { mutableStateOf(unspecifiedColor) }
        themeContentColorHexCode.value = getForegroundColor(
            backgroundColor = themeColorHexCode.value.hexToComposeColor(),
        ).toHex()

        CompositionLocalProvider(
            LocalThemeColor provides themeColorHexCode,
            LocalThemeContentColor provides themeContentColorHexCode,
            LocalSharedTransitionScope provides this@SharedTransitionLayout,
        ) {
            AnimatedContent(targetState = Unit) {
                CompositionLocalProvider(LocalNavAnimatedVisibilityScope provides this) {
                    NavHost(
                        navController = navController,
                        startDestination = SplashScreen,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        tripPlannerDestinations(navController = navController)

                        animComposable<SplashScreen> {
                            val viewModel: SplashViewModel = koinViewModel<SplashViewModel>()
                            val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
                            val mode by viewModel.uiState.collectAsStateWithLifecycle()

                            productClass = mode.productClass
                            themeColorHexCode.value = mode.colorCode

                            SplashScreen(
                                logoColor = if (productClass != null && themeColorHexCode.value != unspecifiedColor) {
                                    themeColorHexCode.value.hexToComposeColor()
                                } else {
                                    KrailTheme.colors.onSurface
                                },
                                backgroundColor = KrailTheme.colors.surface,
                                onSplashComplete = {
                                    navController.navigate(
                                        route = SavedTripsRoute,
                                        navOptions = NavOptions.Builder().setLaunchSingleTop(true)
                                            .setPopUpTo<SplashScreen>(inclusive = true).build(),
                                    )
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Serializable
private data object SplashScreen

internal val DEFAULT_THEME_TRANSPORT_MODE = TransportMode.Train()
