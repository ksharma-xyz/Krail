package xyz.ksharma.krail.trip.planner.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
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
    val themeColor = LocalThemeColor.current
    return if (isSystemInDarkTheme()) {
        themeColor.value.hexToComposeColor().copy(alpha = 0.45f)
    } else {
        themeColor.value.hexToComposeColor().copy(alpha = 0.15f)
    }
}
