package xyz.ksharma.krail.trip.planner.ui.state.usualride

import xyz.ksharma.krail.taj.theme.ThemeColor

data class ThemeSelectionState(
    val selectedThemeColor: ThemeColor? = null,
    val themeSelected: Boolean = false,
)
