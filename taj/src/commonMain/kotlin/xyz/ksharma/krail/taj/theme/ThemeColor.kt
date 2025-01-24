package xyz.ksharma.krail.taj.theme

import androidx.compose.runtime.Immutable

@Immutable
data class ThemeColor(
    val hexColorCode: String,
    val id: Int,
    val tagLine: String,
)

fun getThemeColors(): List<ThemeColor> {
    return listOf(

        ThemeColor(
            hexColorCode = "#F6891F",
            id = 1,
            tagLine = "On the track, no lookin' back!"
        ), // Train

        ThemeColor(
            hexColorCode =
            "#009B77", id = 2, tagLine = "Surf the sub, no cap!"
        ), // Metro

        ThemeColor(
            hexColorCode = "#00B5EF",
            id = 5,
            tagLine = "Hoppin' the concrete jungle!"
        ), // Bus

        ThemeColor(
            hexColorCode = "#742282",
            id = 7,
            tagLine = "Purple drip, endless trip!"
        ), // Coach

        ThemeColor(hexColorCode = "#5AB031", id = 9, tagLine = "Just floatin!"), // Ferry

        ThemeColor(
            hexColorCode = "#F836E5",
            id = 100,
            tagLine = "Mah city, mah rules!"
        ), // Barbie Pink
    )
}
