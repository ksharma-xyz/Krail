package xyz.ksharma.krail.taj.theme

data class ThemeColor(
    val hexColorCode: String,
    val id: Int,
    val tagLine: String,
)

fun getThemeColors(): List<ThemeColor> {
    return listOf(
        ThemeColor("#F6891F", id = 1, tagLine = "On the track, no lookin' back!"), // Train
        ThemeColor("#009B77", id = 2, tagLine = "Surf the sub, no cap!"), // Metro
        ThemeColor("#F836E5", id = 3, tagLine = "Mah city, mah rules!"), // Barbie Pink
        ThemeColor("#00B5EF", id = 5, tagLine = "Hoppin' the concrete jungle!"), // Bus
        ThemeColor("#742282", id = 7, tagLine = "Purple drip, endless trip!"), // Coach
        ThemeColor("#5AB031", id = 9, tagLine = "Just floatin!"), // Ferry
    )
}
