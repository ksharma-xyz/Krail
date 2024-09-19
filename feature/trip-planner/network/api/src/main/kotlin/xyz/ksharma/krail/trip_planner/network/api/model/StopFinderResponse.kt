package xyz.ksharma.krail.trip_planner.network.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StopFinderResponse(
    @SerialName("version")
    val version: String,

    @SerialName("locations")
    val locations: List<Location>,
) {
    @Serializable
    data class Location(
        @SerialName("id")
        val id: String,

        @SerialName("isGlobalId")
        val isGlobalId: Boolean,

        @SerialName("name")
        val name: String,

        @SerialName("disassembledName")
        val disassembledName: String? = null,

        @SerialName("coord")
        val coord: List<Double>,

        @SerialName("type")
        val type: String,

        @SerialName("matchQuality")
        val matchQuality: Int,

        @SerialName("isBest")
        val isBest: Boolean,

        @SerialName("parent")
        val parent: Parent? = null,

        @SerialName("assignedStops")
        val assignedStops: List<AssignedStop>? = null,

        @SerialName("properties")
        val properties: Properties? = null,

        @SerialName("productClasses")
        val productClasses: List<Int>,
    )

    @Serializable
    data class Parent(
        @SerialName("id")
        val id: String? = null,

        @SerialName("name")
        val name: String,

        @SerialName("type")
        val type: String,
    )

    @Serializable
    data class AssignedStop(
        @SerialName("id")
        val id: String,

        @SerialName("isGlobalId")
        val isGlobalId: Boolean,

        @SerialName("name")
        val name: String,

        @SerialName("type")
        val type: String,

        @SerialName("coord")
        val coord: List<Double>,

        @SerialName("parent")
        val parent: Parent,

        @SerialName("productClasses")
        val productClasses: List<Int>,

        @SerialName("connectingMode")
        val connectingMode: Int,

        @SerialName("properties")
        val properties: Properties,
    )

    @Serializable
    data class Properties(
        @SerialName("stopId")
        val stopId: String,
    )
}
