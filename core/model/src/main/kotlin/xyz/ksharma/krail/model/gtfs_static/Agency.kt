package xyz.ksharma.krail.model.gtfs_static

data class Agency(
    val agencyId: String,
    val agencyName: String,
    val agencyUrl: String,
    val agencyTimezone: String,
    val agencyLang: String,
    val agencyPhone: String
)
