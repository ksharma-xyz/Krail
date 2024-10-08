package xyz.ksharma.krail.trip_planner.ui.state.timetable

import kotlinx.collections.immutable.ImmutableList

data class JourneyCardInfo(
    val timeText: String, // "in x mins"
    val platformText: String, // "on Platform X"
    val originTime: String, // "11:30pm"
    val destinationTime: String, // "11:40pm"
    val travelTime: String, // "(10 min)"

    /**
     * transportation.disassembledName -> lineName
     * transportation.product.class -> TransportModeType
     */
    val transportModeLines: ImmutableList<TimeTableState.TransportModeLine>,
)
