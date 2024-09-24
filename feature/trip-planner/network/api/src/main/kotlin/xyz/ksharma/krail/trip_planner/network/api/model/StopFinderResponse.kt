package xyz.ksharma.krail.trip_planner.network.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StopFinderResponse(
    @SerialName("version")
    val version: String? = null,

    @SerialName("locations")
    val locations: List<Location>? = null,
) {
    @Serializable
    data class Location(
        @SerialName("id")
        val id: String? = null,

        @SerialName("isGlobalId")
        val isGlobalId: Boolean? = null,

        @SerialName("name")
        val name: String? = null,

        @SerialName("disassembledName")
        val disassembledName: String? = null,

        @SerialName("coord")
        val coord: List<Double>? = null,

        @SerialName("type")
        val type: String? = null,

        @SerialName("matchQuality")
        val matchQuality: Int? = null,

        @SerialName("isBest")
        val isBest: Boolean? = null,

        @SerialName("parent")
        val parent: Parent? = null,

        @SerialName("assignedStops")
        val assignedStops: List<AssignedStop>? = null,

        @SerialName("properties")
        val properties: Properties? = null,

        @SerialName("productClasses")
        val productClasses: List<Int>? = null,
    )

    @Serializable
    data class Parent(
        @SerialName("id")
        val id: String? = null,

        @SerialName("name")
        val name: String? = null,

        @SerialName("type")
        val type: String? = null,
    )

    @Serializable
    data class AssignedStop(
        @SerialName("id")
        val id: String? = null,

        @SerialName("isGlobalId")
        val isGlobalId: Boolean? = null,

        @SerialName("name")
        val name: String? = null,

        @SerialName("type")
        val type: String? = null,

        @SerialName("coord")
        val coord: List<Double>? = null,

        @SerialName("parent")
        val parent: Parent? = null,

        @SerialName("productClasses")
        val productClasses: List<Int>? = null,

        @SerialName("connectingMode")
        val connectingMode: Int? = null,

        @SerialName("properties")
        val properties: Properties? = null,
    )

    @Serializable
    data class Properties(
        @SerialName("stopId")
        val stopId: String? = null,
    )
}
