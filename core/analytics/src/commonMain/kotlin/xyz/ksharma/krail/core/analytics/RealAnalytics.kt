package xyz.ksharma.krail.core.analytics

import dev.gitlive.firebase.analytics.FirebaseAnalytics
import xyz.ksharma.krail.core.analytics.event.AnalyticsEvent

class RealAnalytics(private val firebaseAnalytics: FirebaseAnalytics) : Analytics {

    override fun track(event: AnalyticsEvent, properties: Map<String, Any>?) {
        firebaseAnalytics.logEvent(event.name, properties)
    }

    override fun setUserId(userId: String) {
        TODO("Not yet implemented")
    }

    override fun setUserProperty(name: String, value: String) {
        TODO("Not yet implemented")
    }
}
