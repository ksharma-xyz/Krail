package xyz.ksharma.krail.trip.planner.ui.state.usualride

import xyz.ksharma.krail.trip.planner.ui.state.TransportMode

data class ThemeSelectionState(
    val selectedTransportMode: TransportMode? = null,
    val themeSelected: Boolean = false,
)
