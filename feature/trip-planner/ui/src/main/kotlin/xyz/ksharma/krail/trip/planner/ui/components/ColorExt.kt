package xyz.ksharma.krail.trip.planner.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import xyz.ksharma.krail.design.system.LocalThemeColor
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode

/**
 * Converts a hexadecimal color string to a Compose Color object.
 *
 * This function takes a string representing a hexadecimal color code
 * (e.g., "#FF0000" for red) and attempts to convert it into a Compose Color
 * object.
 *
 * @throws IllegalArgumentException if the provided string is not a valid
 * hexadecimal color code. A valid code must start with "#" followed by
 * either 6 or 8 hexadecimal digits (0-9, A-F, a-f).
 *
 * @return A Compose Color object representing the provided hex color code.
 */
fun String.hexToComposeColor(): Color {
    require(this.isValidHexColorCode()) { "Invalid hex color code: $this" }
    return Color(android.graphics.Color.parseColor(this))
}

/**
 * Checks if a string is a valid hexadecimal color code.
 *
 * This function uses a regular expression to validate the format of the
 * provided string. A valid hex color code must start with "#" followed by
 * either 6 or 8 hexadecimal digits (0-9, A-F, a-f).
 *
 * @return true if the string is a valid hex color code, false otherwise.
 */
private fun String.isValidHexColorCode(): Boolean {
    // Regular expression to match #RRGGBB or #RRGGBBAA hex color codes
    val hexColorRegex = Regex("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})\$")
    return hexColorRegex.matches(this)
}

/**
 * Will return a color based on the transport mode and the current theme (light / dark).
 *
 * @throws IllegalArgumentException if the provided string is not a valid
 * hexadecimal color code.
 *
 * @return A Compose Color object representing the provided hex color code.
 */
@Composable
internal fun transportModeBackgroundColor(transportMode: TransportMode): Color {
    return if (isSystemInDarkTheme()) {
        transportMode.colorCode.hexToComposeColor().copy(alpha = 0.45f)
    } else {
        transportMode.colorCode.hexToComposeColor().copy(alpha = 0.15f)
    }
}

@Composable
internal fun themeBackgroundColor(): Color {
    val themeColor by LocalThemeColor.current
    return if (isSystemInDarkTheme()) {
        themeColor.hexToComposeColor().copy(alpha = 0.45f)
    } else {
        themeColor.hexToComposeColor().copy(alpha = 0.15f)
    }
}

/**
 * Converts a Compose Color object to a hexadecimal color string.
 *
 * @return A string representing the hexadecimal color code (e.g., "#FF0000" for red).
 */
@Suppress("ImplicitDefaultLocale")
fun Color.toHex(): String {
    val red = (this.red * 255).toInt()
    val green = (this.green * 255).toInt()
    val blue = (this.blue * 255).toInt()
    val alpha = (this.alpha * 255).toInt()
    return String.format("#%02X%02X%02X%02X", alpha, red, green, blue)
}

/**
 * The default light scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

/**
 * The default dark scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)

/**
 * Update the theme color to make text more readable on top of it.
 *
 * Steps
 * 1. Convert to HSL, so we can modify the lightness without changing the core hue and saturation.
 * For 0xFFEE343F (R: 238, G: 52, B: 63):  Hue ≈ 355°, Saturation ≈ 85%, Lightness ≈ 57%
 * 2. Increase lightness by 20%
 * 3. Convert back to RGB
 *
 * @param color The color to brighten, in ARGB format.
 * @param factor The factor by which to brighten the color (default is 20%)
 *
 * @return The brightened color in ARGB format
 */
fun brightenColor(color: Int, factor: Float = 0.2f): Int {
    // Convert the color to RGB components
    val red = android.graphics.Color.red(color) / 255f
    val green = android.graphics.Color.green(color) / 255f
    val blue = android.graphics.Color.blue(color) / 255f

    // Convert RGB to HSL
    val hsl = FloatArray(3)
    android.graphics.Color.RGBToHSV((red * 255).toInt(), (green * 255).toInt(), (blue * 255).toInt(), hsl)

    // Adjust lightness (value) within bounds
    hsl[2] = (hsl[2] + factor).coerceIn(0f, 1f)

    // Convert back to RGB
    return android.graphics.Color.HSVToColor(hsl)
}

/**
 * Extension function to brighten a Compose Color.
 *
 * This function increases the lightness of the given Compose Color by the specified factor.
 * It converts the color to ARGB format, brightens it, and then converts it back to a Compose Color.
 *
 * @param factor The factor by which to brighten the color (default is 20%).
 *
 * @return The brightened Compose [Color].
 */
fun Color.brighten(factor: Float = 0.2f): Color {
    val argb = this.toArgb()
    val brightenedArgb = brightenColor(argb, factor)
    return Color(brightenedArgb)
}

fun darkenColor(color: Int, factor: Float = 0.2f): Int {
    // Convert the color to RGB components
    val red = android.graphics.Color.red(color) / 255f
    val green = android.graphics.Color.green(color) / 255f
    val blue = android.graphics.Color.blue(color) / 255f

    // Convert RGB to HSL
    val hsl = FloatArray(3)
    android.graphics.Color.RGBToHSV((red * 255).toInt(), (green * 255).toInt(), (blue * 255).toInt(), hsl)

    // Adjust lightness (value) within bounds
    hsl[2] = (hsl[2] - factor).coerceIn(0f, 1f)

    // Convert back to RGB
    return android.graphics.Color.HSVToColor(hsl)
}

fun Color.darken(factor: Float = 0.2f): Color {
    val argb = this.toArgb()
    val darkArgb = darkenColor(argb, factor)
    return Color(darkArgb)
}
