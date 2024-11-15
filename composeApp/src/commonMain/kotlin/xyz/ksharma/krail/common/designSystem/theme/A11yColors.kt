package xyz.ksharma.krail.common.designSystem.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

/**
 * Calculates the contrast ratio between two colors
 */
fun Color.contrastRatio(other: Color): Float {
    val luminance1 = this.luminance() + 0.05f
    val luminance2 = other.luminance() + 0.05f
    return if (luminance1 > luminance2) luminance1 / luminance2 else luminance2 / luminance1
}

/**
 * Returns a foreground color that has a contrast ratio of at least 4.0 with the provided background
 * color.
 */
fun getForegroundColor(backgroundColor: Color): Color {
    val lightForegroundColor = md_theme_dark_onSurface
    val darkForegroundColor = md_theme_light_onSurface

    return if (lightForegroundColor.contrastRatio(backgroundColor) >= 4.0f) {
        lightForegroundColor
    } else {
        darkForegroundColor
    }
}

fun shouldUseDarkIcons(backgroundColor: Color): Boolean {
    return getForegroundColor(backgroundColor) == md_theme_dark_onSurface
}
