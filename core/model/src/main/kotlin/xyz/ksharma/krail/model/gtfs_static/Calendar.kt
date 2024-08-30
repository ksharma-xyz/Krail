package xyz.ksharma.krail.model.gtfs_static

data class Calendar(
    val serviceId: String,
    val monday: String,
    val tuesday: String,
    val wednesday: String,
    val thursday: String,
    val friday: String,
    val saturday: String,
    val sunday: String,
    val startDate: String,
    val endDate: String,
)
