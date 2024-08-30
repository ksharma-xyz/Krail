package xyz.ksharma.krail.model.gtfs_static

data class Route(
    val routeId: String,
    val agencyId: String,
    val routeShortName: String,
    val routeLongName: String,
    val routeDesc: String,
    val routeType: Int,
    val routeUrl: String,
    val routeColor: String,
    val routeTextColor: String,
)
