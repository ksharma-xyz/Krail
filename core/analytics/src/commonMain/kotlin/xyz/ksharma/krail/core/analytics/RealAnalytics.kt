package xyz.ksharma.krail.core.analytics

import dev.gitlive.firebase.analytics.FirebaseAnalytics

class RealAnalytics(private val firebaseAnalytics: FirebaseAnalytics) : Analytics {

    override fun track(eventName: String, properties: Map<String, Any>?) {
        firebaseAnalytics.logEvent(eventName, properties)
    }

    override fun setUserId(userId: String) {
        TODO("Not yet implemented")
    }

    override fun setUserProperty(name: String, value: String) {
        TODO("Not yet implemented")
    }
}
