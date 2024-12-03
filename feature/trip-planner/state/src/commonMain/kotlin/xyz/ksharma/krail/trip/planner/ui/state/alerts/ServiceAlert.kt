package xyz.ksharma.krail.trip.planner.ui.state.alerts

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ServiceAlert(
    val heading: String,
    val message: String,
    val priority: ServiceAlertPriority,
) {

    fun toJsonString() = Json.encodeToString(serializer(), this)

    fun isHtmlContent(): Boolean {
        return "<[a-z][\\s\\S]*>".toRegex().containsMatchIn(message)
    }

    @Suppress("ConstPropertyName")
    companion object {
        private const val serialVersionUID: Long = 1L

        fun fromJsonString(json: String) =
            kotlin.runCatching { Json.decodeFromString(serializer(), json) }.getOrNull()
    }
}

@Serializable
enum class ServiceAlertPriority {
    HIGH, MEDIUM, LOW
}
