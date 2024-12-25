package xyz.ksharma.krail.sandook

interface Sandook {

    // region Theme
    fun insertOrReplaceTheme(productClass: Long)
    fun getProductClass(): Long?
    fun clearTheme()
    // endregion

    // region SavedTrip
    fun insertOrReplaceTrip(
        tripId: String,
        fromStopId: String,
        fromStopName: String,
        toStopId: String,
        toStopName: String,
    )

    fun deleteTrip(tripId: String)
    fun selectAllTrips(): List<SavedTrip>
    fun selectTripById(tripId: String): SavedTrip?
    fun clearSavedTrips()
    // endregion

    // region Alerts

    fun getAlerts(journeyId: String): List<SelectServiceAlertsByJourneyId>

    fun clearAlerts()

    fun insertAlerts(journeyId: String, alerts: List<SelectServiceAlertsByJourneyId>)

    // endregion
}
