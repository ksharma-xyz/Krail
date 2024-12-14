package xyz.ksharma.krail.core.analytics

interface Analytics {

    fun track(eventName: String, properties: Map<String, Any>? = null)

    fun setUserId(userId: String)

    fun setUserProperty(name: String, value: String)
}
