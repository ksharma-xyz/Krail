package xyz.ksharma.krail.trip_planner.ui.state.timetable

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Trip(
    val fromStopId: String,
    val fromStopName: String,
    val toStopId: String,
    val toStopName: String,
) {
    fun toJsonString() = Json.encodeToString(serializer(), this)

    companion object {
        fun fromJsonString(json: String) =
            kotlin.runCatching { Json.decodeFromString(serializer(), json) }.getOrNull()
    }
}
