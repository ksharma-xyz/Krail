package xyz.ksharma.krail.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            SplashScreen(onSplashComplete = {
                navController.navigate(
                    route = SavedTripsRoute,
                    navOptions = NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setPopUpTo<SplashScreen>(inclusive = true)
                        .build()
                )
            })
        }
    }
}

@Composable
private fun SplashScreen(onSplashComplete: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = KrailTheme.colors.background),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedKrailLogo()

        val splashComplete by rememberUpdatedState(onSplashComplete)
        LaunchedEffect(key1 = Unit) {
            delay(2000)
            splashComplete()
        }
    }
}

@Composable
fun AnimatedKrailLogo(modifier: Modifier = Modifier) {
    var animationStarted by remember { mutableStateOf(false) }

    // Trigger animation after a delay (e.g., when the splash screen is displayed)
    LaunchedEffect(key1 = Unit) {
        animationStarted = true
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedLetter("K", animationStarted)
        AnimatedLetter("R", animationStarted)
        AnimatedLetter("A", animationStarted)
        AnimatedLetter("I", animationStarted)
        AnimatedLetter("L", animationStarted)
    }
}

@Composable
fun AnimatedLetter(letter: String, animationStarted: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "animeAnimation")

    // Scale animation with anticipation and squash/stretch
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1200
                0.0f at 0 using LinearEasing // Hold initial scale
                0.7f at 200 using FastOutSlowInEasing // Anticipation (quick shrink)
                1.2f at 500 using FastOutSlowInEasing // Squash/stretch (overshoot)
                1.0f at 1200 using FastOutSlowInEasing // Settle back to normal scale
                0.0f at 1500 using LinearEasing // Settle back to 0 scale
            },
            repeatMode = RepeatMode.Reverse
        ), label = "animeAnimation"
    )

    val letterScale = if (animationStarted) scale else 1f

    Text(
        text = letter,
        color = if (isSystemInDarkTheme()) Color(0xFFFFFF33) else Color(0xFFFF69B4),
        style = KrailTheme.typography.displayLarge.copy(
            fontSize = 80.sp,
            letterSpacing = 4.sp,
            fontWeight = FontWeight.ExtraBold,
            drawStyle = Stroke(
                width = 8f, // Adjust stroke width for desired thickness
            )
        ),
        modifier = Modifier
            .graphicsLayer {
                scaleX = letterScale
                scaleY = letterScale
            }
            .padding(4.dp)
    )
}

@Serializable
data object SplashScreen


@PreviewLightDark
@Composable
private fun PreviewSplashScreen() {
    KrailTheme {
        Column(modifier = Modifier.background(color = KrailTheme.colors.background)) {
            AnimatedKrailLogo()
        }
    }
}
