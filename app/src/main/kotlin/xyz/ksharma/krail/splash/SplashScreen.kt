package xyz.ksharma.krail.splash

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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.theme.KrailTheme

@Composable
fun SplashScreen(onSplashComplete: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = KrailTheme.colors.background),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedKrailLogo()

        val splashComplete by rememberUpdatedState(onSplashComplete)
        LaunchedEffect(key1 = Unit) {
            delay(1100)
            splashComplete()
        }
    }
}

@Composable
private fun AnimatedKrailLogo(modifier: Modifier = Modifier) {
    var animationStarted by remember { mutableStateOf(false) }

    // Trigger animation after a delay (e.g., when the splash screen is displayed)
    LaunchedEffect(key1 = Unit) {
        animationStarted = true
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(),
        horizontalArrangement = Arrangement.Center,
    ) {
        AnimatedLetter("K", animationStarted)
        AnimatedLetter("R", animationStarted)
        AnimatedLetter("A", animationStarted)
        AnimatedLetter("I", animationStarted)
        AnimatedLetter("L", animationStarted)
    }
}

@Composable
private fun AnimatedLetter(letter: String, animationStarted: Boolean, modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "animeAnimation")

    // Scale animation with anticipation and squash/stretch
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1100
                0.0f at 0 using LinearEasing // Hold initial scale
                0.7f at 200 using FastOutSlowInEasing // Anticipation (quick shrink)
                1.2f at 500 using FastOutSlowInEasing // Squash/stretch (overshoot)
                1.0f at 1000 using FastOutSlowInEasing // Settle back to normal scale
                1.0f at 1100 using LinearEasing // Keep at normal scale
            },
            repeatMode = RepeatMode.Reverse,
        ),
        label = "animeAnimation",
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
            ),
        ),
        modifier = modifier
            .graphicsLayer {
                scaleX = letterScale
                scaleY = letterScale
            }
            .padding(4.dp),
    )
}

@PreviewLightDark
@Composable
private fun PreviewSplashScreen() {
    KrailTheme {
        Column(modifier = Modifier.background(color = KrailTheme.colors.background)) {
            AnimatedKrailLogo()
        }
    }
}
