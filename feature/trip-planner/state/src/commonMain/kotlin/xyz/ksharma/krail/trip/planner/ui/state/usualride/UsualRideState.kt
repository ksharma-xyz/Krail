package xyz.ksharma.krail.trip.planner.ui.state.usualride

import xyz.ksharma.krail.trip.planner.ui.state.TransportMode

data class UsualRideState(
    val selectedTransportMode: TransportMode? = null,
    val themeSelected: Boolean = false,
)
