package xyz.ksharma.krail.design.system.model

/**
 * Reference - https://en.wikipedia.org/wiki/Module:Adjacent_stations/Sydney_Trains
 */
data class TransportModeLine(
    /**
     * Train / Bus / Ferry etc along with their color codes.
     */
    val transportModeType: TransportModeType,

    /**
     * Line number e.g. T1, T4, F1, F2 etc.
     */
    val lineName: String,

    /**
     * Hexadecimal color code for the Line number e.g. T1 is #f99d1c
     */
    val lineHexColorCode: String,
)
