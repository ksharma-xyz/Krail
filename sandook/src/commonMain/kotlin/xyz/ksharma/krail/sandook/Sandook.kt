package xyz.ksharma.krail.sandook

import xyz.ksharma.krail.trip.planner.ui.state.alerts.ServiceAlert

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
        toStopName: String
    )
    fun deleteTrip(tripId: String)
    fun selectAllTrips(): List<SavedTrip>
    fun selectTripById(tripId: String): SavedTrip?
    fun clearSavedTrips()
    // endregion

    // region ServiceAlert
    fun insertOrReplaceServiceAlert(
        journeyId: String, serviceAlerts: List<ServiceAlert>
    )
    fun deleteServiceAlert(journeyId: String)
    fun selectAllServiceAlerts(): List<ServiceAlertsTable>
    fun selectServiceAlertById(journeyId: String): List<SelectServiceAlertsByJourneyId>?
    fun clearAllServiceAlerts()
}
