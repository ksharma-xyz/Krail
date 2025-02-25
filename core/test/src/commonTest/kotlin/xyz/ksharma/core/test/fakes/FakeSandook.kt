package xyz.ksharma.core.test.fakes

import xyz.ksharma.krail.sandook.NswStops
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.sandook.SavedTrip
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

    override fun insertNswStopProductClass(stopId: String, productClass: Int) {
        val productClasses = stopProductClasses.getOrPut(stopId) { mutableListOf() }
        productClasses.add(productClass)
    }

    override fun selectStopsByPartialName(stopName: String): List<NswStops> {
        return stops.filter { it.stopName.contains(stopName, ignoreCase = true) }
    }

    override fun selectStopsByNameAndProductClass(
        stopName: String,
        includeProductClassList: List<Int>,
    ): List<NswStops> {
        return stops.filter { stop ->
            stop.stopName.contains(stopName, ignoreCase = true) &&
                    stopProductClasses[stop.stopId]?.any { it in includeProductClassList } == true
        }
    }

    override fun selectStopsByNameExcludingProductClass(
        stopName: String,
        excludeProductClassList: List<Int>,
    ): List<NswStops> {
        return stops.filter { stop ->
            stop.stopName.contains(stopName, ignoreCase = true) &&
                    stopProductClasses[stop.stopId]?.none { it in excludeProductClassList } == true
        }
    }

    override fun selectStopsByNameExcludingProductClassOrExactId(
        stopName: String,
        excludeProductClassList: List<Int>,
    ): List<NswStops> {
        return stops.filter { stop ->
            (stop.stopName.contains(stopName, ignoreCase = true) ||
                    stop.stopId == stopName) &&
                    stopProductClasses[stop.stopId]?.none { it in excludeProductClassList } == true
        }
    }
}
