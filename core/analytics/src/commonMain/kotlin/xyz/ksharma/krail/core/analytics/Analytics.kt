package xyz.ksharma.krail.core.analytics

import xyz.ksharma.krail.core.analytics.event.AnalyticsEvent

interface Analytics {

    fun track(event: AnalyticsEvent, properties: Map<String, Any>? = null)

    fun setUserId(userId: String)

    fun setUserProperty(name: String, value: String)
}
