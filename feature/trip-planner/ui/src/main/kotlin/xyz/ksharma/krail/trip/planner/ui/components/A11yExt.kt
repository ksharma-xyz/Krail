package xyz.ksharma.krail.trip.planner.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity

/**
 * Display the content only if the font scale is less than the threshold.
 */
@Composable
fun DisplayWithDensityCheck(fontScaleThreshold: Float = 1.8f, content: @Composable () -> Unit) {
    val density = LocalDensity.current
    if (density.fontScale < fontScaleThreshold) {
        content()
    }
}
