package xyz.ksharma.core.test.fakes

import xyz.ksharma.krail.core.analytics.Analytics
import xyz.ksharma.krail.core.analytics.event.AnalyticsEvent

class FakeAnalytics : Analytics {

    private val trackedEvents = mutableListOf<String>()
    private var fakeUserId: String? = null

    fun wasScreenViewEventTracked(event: String): Boolean {
        println("trackedEvents: $trackedEvents")
        return trackedEvents.contains(event)
    }

    override fun track(event: AnalyticsEvent) {
        println("Tracking event: $event")
        trackedEvents.add(event.name)
    }

    override fun setUserId(userId: String) {
        fakeUserId = userId
    }

    override fun setUserProperty(name: String, value: String) {
        println("Setting user property $name to $value")
    }
}
