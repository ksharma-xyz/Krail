package xyz.ksharma.core.test.fakes

import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.sandook.SavedTrip
import xyz.ksharma.krail.sandook.SelectServiceAlertsByJourneyId

class FakeSandook : Sandook {

    private var productClass: Long? = null
    private val trips = mutableListOf<SavedTrip>()
    private val alerts = mutableMapOf<String, List<SelectServiceAlertsByJourneyId>>()

    override fun insertOrReplaceTheme(productClass: Long) {
        this.productClass = productClass
    }

    override fun getProductClass(): Long? {
        return productClass
    }

    override fun clearTheme() {
        productClass = null
    }

    override fun insertOrReplaceTrip(
        tripId: String,
        fromStopId: String,
        fromStopName: String,
        toStopId: String,
        toStopName: String,
    ) {
        val trip = SavedTrip(
            tripId,
            fromStopId,
            fromStopName,
            toStopId,
            toStopName,
            timestamp = null,
        )
        if (trip.tripId == tripId) {
            trips.remove(trip)
        }
        trips.add(trip)
    }

    override fun deleteTrip(tripId: String) {
        trips.find { it.tripId == tripId }?.let { trip ->
            trips.remove(trip)
        }
    }

    override fun selectAllTrips(): List<SavedTrip> {
        return trips.toList()
    }

    override fun selectTripById(tripId: String): SavedTrip? {
        return trips.find { it.tripId == tripId }
    }

    override fun clearSavedTrips() {
        trips.clear()
    }

    override fun getAlerts(journeyId: String): List<SelectServiceAlertsByJourneyId> {
        return alerts[journeyId] ?: emptyList()
    }

    override fun clearAlerts() {
        alerts.clear()
    }

    override fun insertAlerts(journeyId: String, alerts: List<SelectServiceAlertsByJourneyId>) {
        this.alerts[journeyId] = alerts
    }
}
