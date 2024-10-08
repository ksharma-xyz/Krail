package xyz.ksharma.krail.trip_planner.ui.state

/**
 * Reference - https://en.wikipedia.org/wiki/Module:Adjacent_stations/Sydney_Trains
 */
data class TransportModeLine(
    /**
     * Train / Bus / Ferry etc along with their color codes.
     */
    val transportMode: TransportMode,

    /**
     * Line number e.g. T1, T4, F1, F2 etc.
     */
    val lineName: String,
)
