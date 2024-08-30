package xyz.ksharma.krail.model.gtfs_static

data class StopTimes(
    val trip_id: String,
    val arrival_time: String,
    val departure_time: String,
    val stop_id: String,
    val stop_sequence: Int?,
    val stop_headsign: String,
    val pickup_type: Int?,
    val drop_off_type: Int?,
    val shape_dist_traveled: String? = null,
)
// TODO - make unused fields nullable.
