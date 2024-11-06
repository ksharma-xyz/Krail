package xyz.ksharma.krail.trip.planner.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity

@Composable
fun WithDensityCheck(fontScaleThreshold: Float = 1.8f, content: @Composable () -> Unit) {
    val density = LocalDensity.current
    if (density.fontScale < fontScaleThreshold) {
        content()
    }
}
