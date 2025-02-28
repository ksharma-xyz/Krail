package xyz.ksharma.core.test.fakes

import xyz.ksharma.krail.sandook.NswStops
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.sandook.SavedTrip
import xyz.ksharma.krail.sandook.SelectProductClassesForStop
import xyz.ksharma.krail.sandook.SelectServiceAlertsByJourneyId

class FakeSandook : Sandook {

    private var productClass: Long? = null
    private val trips = mutableListOf<SavedTrip>()
    private val alerts = mutableMapOf<String, List<SelectServiceAlertsByJourneyId>>()
    private val stops = mutableListOf<NswStops>()
    private val stopProductClasses = mutableMapOf<String, MutableList<Int>>()

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

    override fun insertNswStop(stopId: String, stopName: String, stopLat: Double, stopLon: Double) {
        stops.add(NswStops(stopId, stopName, stopLat, stopLon))
    }

    override fun stopsCount(): Int {
        return stops.size
    }

    override fun productClassCount(): Int {
        return stopProductClasses.size
    }

    override fun insertNswStopProductClass(stopId: String, productClass: Int) {
        val productClasses = stopProductClasses.getOrPut(stopId) { mutableListOf() }
        productClasses.add(productClass)
    }

    override fun insertTransaction(block: () -> Unit) {
        block()
    }

    override fun clearNswStopsTable() {
        stops.clear()
    }

    override fun clearNswProductClassTable() {
        stopProductClasses.clear()
    }

    override fun selectStops(
        stopName: String,
        excludeProductClassList: List<Int>,
    ): List<SelectProductClassesForStop> {
        return stops.map { stop ->
            SelectProductClassesForStop(
                stop.stopId,
                stop.stopName,
                stop.stopLat,
                stop.stopLon,
                productClasses = stopProductClasses[stop.stopId]?.joinToString(",") ?: ""
            )
        }
    }
}
