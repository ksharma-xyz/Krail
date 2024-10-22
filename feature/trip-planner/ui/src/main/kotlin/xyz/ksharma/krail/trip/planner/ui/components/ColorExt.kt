package xyz.ksharma.krail.trip.planner.ui.components

import androidx.compose.ui.graphics.Color

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
internal fun String.hexToComposeColor(): Color =
    if (this.isValidHexColorCode()) {
        Color(android.graphics.Color.parseColor(this))
    } else {
        throw IllegalArgumentException("Invalid hex color code: $this")
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
