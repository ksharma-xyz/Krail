package xyz.ksharma.krail.trip_planner.ui.state.timetable

data class Trip(
    val fromStopId: String,
    val fromStopName: String,
    val toStopId: String,
    val toStopName: String,
)
