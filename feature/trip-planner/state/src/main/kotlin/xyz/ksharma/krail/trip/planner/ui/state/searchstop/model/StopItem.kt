package xyz.ksharma.krail.trip.planner.ui.state.searchstop.model

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.serialization.json.Json
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import java.io.Serializable

/**
 * Represents a Stop item in the search results when searching for stops.
 * Need to be Serializable because it is passed as a parameter during navigation.
 */
@kotlinx.serialization.Serializable
data class StopItem(
    val stopName: String,
    val transportModes: ImmutableSet<TransportMode> = persistentSetOf(),
    val stopId: String,
) : Serializable {
    fun toJsonString() = Json.encodeToString(serializer(), this)

    companion object {
        fun fromJsonString(json: String) =
            kotlin.runCatching { Json.decodeFromString(serializer(), json) }.getOrNull()
    }
}
