package xyz.ksharma.krail.core.analytics

import dev.gitlive.firebase.analytics.FirebaseAnalytics
import xyz.ksharma.krail.core.analytics.event.AnalyticsEvent
import xyz.ksharma.krail.core.appinfo.AppInfoProvider

class RealAnalytics(
    private val firebaseAnalytics: FirebaseAnalytics,
    private val appInfoProvider: AppInfoProvider,
) : Analytics {

    override fun track(event: AnalyticsEvent) {
        // Only track prod builds analytics events
        if (appInfoProvider.getAppInfo().isDebug.not()) {
            firebaseAnalytics.logEvent(event.name, event.properties)
        }
    }

    override fun setUserId(userId: String) {
        TODO("Not yet implemented")
    }

    override fun setUserProperty(name: String, value: String) {
        TODO("Not yet implemented")
    }
}
