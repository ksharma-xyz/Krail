package xyz.ksharma.krail.taj

import androidx.compose.ui.graphics.Color

fun String.hexToComposeColor(): Color {
    require(isValidHexColorCode()) {
        "Invalid hex color code: $this. Hex color codes must be in the format #RRGGBB or #AARRGGBB."
    }

    // Remove the leading '#' if present
    val hex = removePrefix("#")

    // Parse the hex value
    return when (hex.length) {
        6 -> {
            // If the string is in the format RRGGBB, add full opacity (FF) at the start
            val r = hex.substring(0, 2).toInt(16)
            val g = hex.substring(2, 4).toInt(16)
            val b = hex.substring(4, 6).toInt(16)
            Color(red = r / 255f, green = g / 255f, blue = b / 255f)
        }
        8 -> {
            // If the string is in the format AARRGGBB
            val a = hex.substring(0, 2).toInt(16)
            val r = hex.substring(2, 4).toInt(16)
            val g = hex.substring(4, 6).toInt(16)
            val b = hex.substring(6, 8).toInt(16)
            Color(alpha = a / 255f, red = r / 255f, green = g / 255f, blue = b / 255f)
        }
        else -> throw IllegalArgumentException("Invalid hex color format. Use #RRGGBB or #AARRGGBB.")
    }
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
