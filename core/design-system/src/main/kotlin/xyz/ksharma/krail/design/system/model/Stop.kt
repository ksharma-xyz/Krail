package xyz.ksharma.krail.design.system.model
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
