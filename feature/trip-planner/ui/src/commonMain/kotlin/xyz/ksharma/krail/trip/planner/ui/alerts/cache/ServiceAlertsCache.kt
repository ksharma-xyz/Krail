package xyz.ksharma.krail.trip.planner.ui.alerts.cache

import xyz.ksharma.krail.trip.planner.ui.state.alerts.ServiceAlert

interface ServiceAlertsCache {

    fun getAlerts(journeyId: String): List<ServiceAlert>?

    fun setAlerts(journeyId: String, alerts: List<ServiceAlert>)

    fun clearAlerts()
}

class RealServiceAlertsCache : ServiceAlertsCache {

    init {
        println("RealServiceAlertsCache created $this")
    }

    private val serviceAlerts: MutableMap<String, List<ServiceAlert>> = mutableMapOf()

    override fun getAlerts(journeyId: String): List<ServiceAlert>? {
        return serviceAlerts[journeyId]
    }

    override fun setAlerts(journeyId: String, alerts: List<ServiceAlert>) {
        serviceAlerts.clear()
        serviceAlerts[journeyId] = alerts
    }

    override fun clearAlerts() {
        serviceAlerts.clear()
    }
}
