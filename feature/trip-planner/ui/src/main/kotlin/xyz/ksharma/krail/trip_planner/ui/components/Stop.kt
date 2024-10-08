package xyz.ksharma.krail.trip_planner.ui.components
data class Stop(
    /**
     * E.g. TownHall Station, Platform 1
     */
    val name: String,

    /**
     * Departure Time
     */
    val departureTime: String,
)
