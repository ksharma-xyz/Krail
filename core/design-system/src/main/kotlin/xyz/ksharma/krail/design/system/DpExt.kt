package xyz.ksharma.krail.design.system

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
internal fun Dp.toAdaptiveSize(): Dp {
    val density = LocalDensity.current
    return this.times(density.fontScale)
}
