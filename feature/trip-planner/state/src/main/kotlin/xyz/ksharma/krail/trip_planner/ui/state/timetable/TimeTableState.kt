package xyz.ksharma.krail.trip_planner.ui.state.timetable

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import xyz.ksharma.krail.trip_planner.domain.model.TransportModeType

data class TimeTableState(
    val journeyList: ImmutableList<Journey> = persistentListOf(),
) {

    data class Journey(
        val departureText: String,
        val timeText: String,
        val transportModeLineList: ImmutableList<TransportModeLine>,
    )

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
}
