package xyz.ksharma.krail.trip.planner.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import org.junit.Assert.assertEquals
import org.junit.Test

// Define the colors
private val myWhiteLightMode = Color(0xFFFFFBFF)
private val myBlackLightMode = Color(0xFF1F1B16)
private val myWhiteDarkMode = Color(0xFFEAE1D9)
private val myBlackDarkMode = Color(0xFF1F1B16)

private val busTheme = Color(0xFF00B5EF)
private val trainTheme = Color(0xFFF6891F)
private val metroTheme = Color(0xFF009B77)
private val ferryTheme = Color(0xFF5AB031)
private val coachTheme = Color(0xFF742282)
private val lightRailTheme = Color(0xFFEE343F)

fun Color.contrastRatio(other: Color): Float {
    val luminance1 = this.luminance() + 0.05f
    val luminance2 = other.luminance() + 0.05f
    return if (luminance1 > luminance2) luminance1 / luminance2 else luminance2 / luminance1
}

fun getForegroundColor(backgroundColor: Color, isDarkMode: Boolean): Color {
    val whiteColor = if (isDarkMode) myWhiteDarkMode else myWhiteLightMode
    val blackColor = if (isDarkMode) myBlackDarkMode else myBlackLightMode
    val whiteContrast = whiteColor.contrastRatio(backgroundColor)
    val blackContrast = blackColor.contrastRatio(backgroundColor)

    println("Background: ${backgroundColor.toHex()}, White Contrast: $whiteContrast, Black Contrast: $blackContrast")

    return if (whiteContrast >= 4.0f) whiteColor else blackColor
}

fun Color.toHex(): String {
    val red = (this.red * 255).toInt()
    val green = (this.green * 255).toInt()
    val blue = (this.blue * 255).toInt()
    val alpha = (this.alpha * 255).toInt()
    return String.format("#%02X%02X%02X%02X", alpha, red, green, blue)
}

class ColorUtilsTest {

    @Test
    fun testGetForegroundColor_lightMode() {
        assertEquals("#FF1F1B16", getForegroundColor(busTheme, false).toHex())
        assertEquals("#FF1F1B16", getForegroundColor(trainTheme, false).toHex())
        assertEquals("#FF1F1B16", getForegroundColor(metroTheme, false).toHex())
        assertEquals("#FF1F1B16", getForegroundColor(ferryTheme, false).toHex())
        assertEquals("#FFFFFBFF", getForegroundColor(coachTheme, false).toHex())
        assertEquals("#FF1F1B16", getForegroundColor(lightRailTheme, false).toHex())
    }

    @Test
    fun testGetForegroundColor_darkMode() {
        assertEquals("#FF1F1B16", getForegroundColor(busTheme, true).toHex())
        assertEquals("#FF1F1B16", getForegroundColor(trainTheme, true).toHex())
        assertEquals("#FF1F1B16", getForegroundColor(metroTheme, true).toHex())
        assertEquals("#FF1F1B16", getForegroundColor(ferryTheme, true).toHex())
        assertEquals("#FFEAE1D9", getForegroundColor(coachTheme, true).toHex())
        assertEquals("#FF1F1B16", getForegroundColor(lightRailTheme, true).toHex())
    }
}
