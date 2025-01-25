package xyz.ksharma.krail.trip.planner.ui.state.usualride

import xyz.ksharma.krail.taj.theme.KrailThemeStyle

data class ThemeSelectionState(
    val selectedThemeStyle: KrailThemeStyle? = null,
    val themeSelected: Boolean = false,
)
