package xyz.ksharma.krail.trip_planner.ui.state.timetable

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import xyz.ksharma.krail.trip_planner.ui.state.TransportModeType

data class TimeTableState(
    val isLoading: Boolean = false,
    val journeyList: ImmutableList<JourneyCardInfo> = persistentListOf(),
) {
    data class JourneyCardInfo(
        val timeText: String, // "in x mins" - journeys.legs.first().origin.departureTimePlanned with Time.now()

        val platformText: String, // "on Platform X" - journeys.legs.first().stopSequence.disassembledName

        // If first leg is not walking then,
        //      leg.first.origin.departureTimeEstimated ?: leg.first.origin.departureTimePlanned
        // else leg.first.destination.arrivalTimeEstimated ?: leg.first.destination.arrivalTimePlanned
        val originTime: String, // "11:30pm" stopSequence.arrivalTimeEstimated ?: stopSequence.arrivalTimePlanned

        // legs.last.destination.arrivalTimeEstimated ?: legs.last.destination.arrivalTimePlanned
        val destinationTime: String, // "11:40pm"

        // legs.sumBy { it.duration } - seconds
        val travelTime: String, // "(10 min)"

        /**
         * transportation.disassembledName -> lineName
         * transportation.product.class -> TransportModeType
         */
        val transportModeLines: ImmutableList<TransportModeLine>,
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
        //val lineHexColorCode: String,
    )
}
