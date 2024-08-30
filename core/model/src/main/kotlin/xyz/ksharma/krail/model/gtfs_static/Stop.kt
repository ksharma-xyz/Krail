package xyz.ksharma.krail.model.gtfs_static

import xyz.ksharma.krail.model.gtfs_realtime.proto.Stop

/**
 * Represents stops.txt
 */
data class Stop(

    val stop_id: String,

    val stop_code: String,

    val stop_name: String,

    val stop_desc: String,

    val stop_lat: String,

    val stop_lon: String,

    val zone_id: String,

    val stop_url: String,

    val location_type: String,

    val parent_station: String,

    val stop_timezone: String,

    // nullable because converting scalar int to concrete types, with GTFS realtime, this should be fine.
    val wheelchair_boarding: Stop.WheelchairBoarding?,
)
