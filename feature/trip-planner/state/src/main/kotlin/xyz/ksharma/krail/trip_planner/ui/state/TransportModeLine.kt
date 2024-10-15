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

    val lineColorCode: String? = calculateLineColorCode(
        lineName = lineName,
        isTrain = transportMode == TransportMode.Train()
    ),
) {
    enum class TransportLine(val key: String, val hexColor: String) {
        NORTH_SHORE_WESTERN("T1", "#f99d1c"),
        LEPPINGTON_INNER_WEST("T2", "#0098cd"),
        LIVERPOOL_INNER_WEST("T3", "#f37021"),
        EASTERN_SUBURBS_ILLAWARRA("T4", "#005aa3"),
        CUMBERLAND("T5", "#c4258f"),
        LIDCOMBE_BANKSTOWN("T6", "#7d3f21"), // FUTURE
        OLYMPIC_PARK("T7", "#6f818e"),
        AIRPORT_SOUTH("T8", "#00954c"),
        NORTHERN("T9", "#d11f2f");
    }
}

private fun calculateLineColorCode(lineName: String, isTrain: Boolean) =
    TransportModeLine.TransportLine.entries.firstOrNull { it.key == lineName && isTrain }?.hexColor
