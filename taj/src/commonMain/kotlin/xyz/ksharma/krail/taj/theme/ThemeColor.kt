package xyz.ksharma.krail.taj.theme

import androidx.compose.runtime.Immutable

val DEFAULT_THEME_STYLE = KrailThemeStyle.Train

/*
@Immutable
data class ThemeColor(
    val hexColorCode: String,
    val id: Int,
    val tagLine: String,
)

*/
enum class KrailThemeStyle(val hexColorCode: String, val id: Int, val tagLine: String) {
    Train(
        hexColorCode = "#F6891F",
        id = 1,
        tagLine = "On the track, no lookin' back!"
    ),
    Metro(
        hexColorCode = "#009B77",
        id = 2,
        tagLine = "Surf the sub, no cap!"
    ),
    Bus(
        hexColorCode = "#00B5EF",
        id = 5,
        tagLine = "Hoppin' the concrete jungle!"
    ),
    Coach(
        hexColorCode = "#742282",
        id = 7,
        tagLine = "Purple drip, endless trip!"
    ),
    Ferry(
        hexColorCode = "#5AB031",
        id = 9,
        tagLine = "Just floatin!"
    ),
    BarbiePink(
        hexColorCode = "#F836E5",
        id = 100,
        tagLine = "Mah city, mah rules!"
    ),
}

/*fun getThemeColors(): List<ThemeColor> {
    return KrailThemeStyle.entries.map {
        ThemeColor(
            hexColorCode = it.hexColorCode,
            id = it.id,
            tagLine = it.tagLine
        )
    }
}*/
