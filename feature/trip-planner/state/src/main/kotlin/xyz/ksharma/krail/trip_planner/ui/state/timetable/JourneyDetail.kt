package xyz.ksharma.krail.trip_planner.ui.state.timetable

import kotlinx.collections.immutable.ImmutableList

data class JourneyDetail(
    val time: String, //"in x mins"
    val platform: String, // "on Platform X"
    val legs: ImmutableList<Leg>,
)

data class Leg(
    val transportModeLine: TimeTableState.TransportModeLine,
    val displayText: String, // "towards X via X"
    val stopsInfo: String, // "4 stops (12 min)"
    val stops: ImmutableList<Stop>,
)

data class Stop(
    val name: String, // "xx Station, Platofrm 1"
    val time: String, // "12:00pm"
)
