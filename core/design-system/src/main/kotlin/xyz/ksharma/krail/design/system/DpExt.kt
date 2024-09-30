package xyz.ksharma.krail.design.system

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
internal fun Dp.toAdaptiveSize(): Dp {
    val density = LocalDensity.current
    return when {
        density.fontScale > 1.5f -> this.times(1.6f)
        density.fontScale > 1f -> this.times(density.fontScale)
        else -> this
    }
}
