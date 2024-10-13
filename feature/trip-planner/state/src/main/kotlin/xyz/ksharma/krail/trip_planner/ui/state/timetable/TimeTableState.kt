package xyz.ksharma.krail.trip_planner.ui.state.timetable

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import xyz.ksharma.krail.trip_planner.ui.state.TransportModeLine

data class TimeTableState(
    val isLoading: Boolean = false,
    val journeyList: ImmutableList<JourneyCardInfo> = persistentListOf(),
) {
    data class JourneyCardInfo(
        val timeText: String, // "in x mins" - journeys.legs.first().origin.departureTimePlanned with Time.now()

        val platformText: String? = null, // "on Platform X" - journeys.legs.first().stopSequence.disassembledName

        // If first leg is not walking then,
        //      leg.first.origin.departureTimeEstimated ?: leg.first.origin.departureTimePlanned
        // else leg.first.destination.arrivalTimeEstimated ?: leg.first.destination.arrivalTimePlanned
        val originTime: String, // "11:30pm" stopSequence.arrivalTimeEstimated ?: stopSequence.arrivalTimePlanned

        val originUtcDateTime: String, // "2024-09-24T19:00:00Z"

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
}
