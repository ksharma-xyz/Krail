package xyz.ksharma.krail.model.gtfs_static

data class Occupancy(
    val tripId: String,
    val stopSequence: String,
    val occupancyStatus: String,
    val monday: String,
    val tuesday: String,
    val wednesday: String,
    val thursday: String,
    val friday: String,
    val saturday: String,
    val sunday: String,
    val startDate: String,
    val endDate: String,
    val exception: String,
)
