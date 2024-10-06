package xyz.ksharma.krail.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip_planner.ui.navigation.SavedTripsRoute
import xyz.ksharma.krail.trip_planner.ui.navigation.tripPlannerDestinations

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
fun KrailNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SplashScreen,
        modifier = Modifier.fillMaxSize()
    ) {
        tripPlannerDestinations(navController = navController)

        composable<SplashScreen> {
            SplashScreen {
                navController.navigate(
                    route = SavedTripsRoute,
                    navOptions = NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setPopUpTo<SplashScreen>(inclusive = true)
                        .build()
                )
            }
        }
    }
}

@Composable
private fun SplashScreen(modifier: Modifier = Modifier, onSplashComplete: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = KrailTheme.colors.background),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "Krail", style = KrailTheme.typography.titleLarge)

        // TODO
        //  - Add Splash Screen Anim here
        //  - Load Config

        LaunchedEffect(key1 = Unit) {
            delay(1000)
            onSplashComplete()
        }
    }
}

@Serializable
data object SplashScreen
