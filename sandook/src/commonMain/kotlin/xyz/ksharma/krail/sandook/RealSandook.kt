package xyz.ksharma.krail.sandook

import xyz.ksharma.krail.trip.planner.ui.state.alerts.ServiceAlert

internal class RealSandook(factory: SandookDriverFactory) : Sandook {

    private val sandook = KrailSandook(factory.createDriver())
    private val query = sandook.krailSandookQueries

    // region Theme
    override fun insertOrReplaceTheme(productClass: Long) {
        query.insertOrReplaceProductClass(productClass)
    }

    override fun getProductClass(): Long? {
        return query.selectProductClass().executeAsOneOrNull()
    }

    override fun clearTheme() {
        query.clearTheme()
    }

    // endregion

    // region SavedTrip
    override fun insertOrReplaceTrip(
        tripId: String,
        fromStopId: String,
        fromStopName: String,
        toStopId: String,
        toStopName: String,
    ) {
        query.insertOrReplaceTrip(
            tripId,
            fromStopId,
            fromStopName,
            toStopId,
            toStopName,
        )
    }

    override fun deleteTrip(tripId: String) {
        query.deleteTrip(tripId)
    }
    // endregion

    // region ServiceAlert
    override fun selectAllTrips(): List<SavedTrip> {
        return query.selectAllTrips().executeAsList()
    }

    override fun selectTripById(tripId: String): SavedTrip? {
        return query.selectTripById(tripId).executeAsOneOrNull()
    }

    override fun clearSavedTrips() {
        query.clearSavedTrips()
    }

    override fun insertOrReplaceServiceAlert(journeyId: String, serviceAlerts: List<ServiceAlert>) {
        serviceAlerts.forEach { serviceAlert ->
            query.insertOrReplaceServiceAlert(
                journeyId,
                serviceAlert.heading,
                serviceAlert.message,
            )
        }
    }

    override fun deleteServiceAlert(journeyId: String) {
        serviceAlertQuery.clearServiceAlertByJourneyId(journeyId)
    }

    override fun selectAllServiceAlerts(): List<ServiceAlertsTable> {
        return serviceAlertQuery.selectAllServiceAlerts().executeAsList()
    }

    override fun selectServiceAlertById(journeyId: String): List<SelectServiceAlertsByJourneyId> {
        return serviceAlertQuery.selectServiceAlertsByJourneyId(journeyId).executeAsList()
    }

    override fun clearAllServiceAlerts() {
        serviceAlertQuery.clearAllServiceAlerts()
    }
    // endregion
}
