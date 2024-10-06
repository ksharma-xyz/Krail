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

        /**
         * This determines whether the id property is a global stop id or not
         */
        @SerialName("isGlobalId")
        val isGlobalId: Boolean? = null,

        /**
         * This is the long version of the location name, which may include the suburb or other information.
         */
        @SerialName("name")
        val name: String? = null,

        /**
         * This is the short version of the location name, which does not include the suburb or
         * other information.
         */
        @SerialName("disassembledName")
        val disassembledName: String? = null,

        @SerialName("coord")
        val coord: List<Double>? = null,

        /**
         * [ poi, singlehouse, stop, platform, street, locality, suburb, unknown ]
         */
        @SerialName("type")
        val type: String? = null,

        @SerialName("matchQuality")
        val matchQuality: Int? = null,

        @SerialName("isBest")
        val isBest: Boolean? = null,

        /**
         * Describes a parent location. Locations are hierarchical, mean a location has a parent, and
         * a location may have any number of child locations. A parent location is often included with locations,
         * which can help traverse the location tree.
         */
        @SerialName("parent")
        val parentLocation: ParentLocation? = null,

        /**
         * This is a list of stops that are assigned to this location. This means if you're in the current location and want to catch
         * public transport, these assigned stops are directly available to you.
         */
        @SerialName("assignedStops")
        val assignedStops: List<AssignedStop>? = null,

        @SerialName("properties")
        val properties: Properties? = null,

        /**
         * This is included only if the type value is set to stop. Contains a list of modes of transport
         * that service this stop. This can be useful for showing relevant way finding icons when presenting users
         * with a list of matching stops to choose from.
         *
         * The following values may be present:
         *
         * 1: Train
         * 2: Metro
         * 4: Light Rail
         * 5: Bus
         * 7: Coach
         * 9: Ferry
         * 11: School Bus
         */
        @SerialName("productClasses")
        val productClasses: List<Int>? = null,
    )

    @Serializable
    data class ParentLocation(
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
        val parentLocation: ParentLocation? = null,

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
