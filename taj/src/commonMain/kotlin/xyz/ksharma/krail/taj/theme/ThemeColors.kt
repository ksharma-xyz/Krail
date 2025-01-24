package xyz.ksharma.krail.taj.theme

data class ThemeColors(
    val hexColorCode: String,
    val id: Int,
)

fun getThemeColors(): List<ThemeColors> {
    return listOf(
        ThemeColors("#F6891F", 1),
        ThemeColors("#009B77", 2),
        ThemeColors("#5AB031", 3),
        ThemeColors("#00B5EF", 4),
        ThemeColors("#F836E5", 5),
    )
}
