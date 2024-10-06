package xyz.ksharma.krail.trip_planner.domain.model

/**
 * NSW Transport Key to icons and line codes
 * https://transportnsw.info/plan/help/key-to-icons-line-codes
 */
data class TransportMode(

    /**
     * The product class number as returned by the StopFinder API.
     */
    val productClassNumber: Int,

    /**
     * Map product class number to an internal concrete type for the transport mode.
     */
    val modeType: TransportModeType? = when (productClassNumber) {
        1 -> TransportModeType.Train
        2 -> TransportModeType.Metro
        4 -> TransportModeType.LightRail
        5 -> TransportModeType.Bus
        7 -> TransportModeType.Coach
        9 -> TransportModeType.Ferry
        11 -> TransportModeType.SchoolBus
        else -> null
    },
) {
    enum class TransportModeType(val modeName: String, val priority: Int, productClass: Int) {
        Train(modeName = "Train", priority = 1111, productClass = 1),
        Metro(modeName = "Metro", priority = 2222, productClass = 2),
        Ferry(modeName = "Ferry", priority = 3333, productClass = 9), // Lesser Ferry than bus
        Bus(
            modeName = "Bus",
            priority = 4444,
            productClass = 5,
        ), // lot of results, therefore slipping down.
        LightRail(modeName = "Light Rail", priority = 5555, productClass = 4),
        SchoolBus(modeName = "School Bus", priority = 6666, productClass = 11),
        Coach(modeName = "Coach", priority = 7777, productClass = 7),
    }
}
