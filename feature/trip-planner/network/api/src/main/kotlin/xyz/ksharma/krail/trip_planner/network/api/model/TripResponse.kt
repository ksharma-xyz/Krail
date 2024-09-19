package xyz.ksharma.krail.trip_planner.network.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TripResponse(
    @SerialName("systemMessages") val systemMessages: List<SystemMessages>,
    @SerialName("journeys") val journeys: List<Journey>,
    @SerialName("error") val error: Error,
    @SerialName("version") val version: String,
) {
    @Serializable
    data class Error(
        @SerialName("versions") val versions: Versions,
        @SerialName("message") val message: String,
    )

    @Serializable
    data class Versions(
        @SerialName("controller") val controller: String,
        @SerialName("interfaceMax") val interfaceMax: String,
        @SerialName("interfaceMin") val interfaceMin: String,
    )

    @Serializable
    data class Journey(
        @SerialName("legs") val legs: List<Leg>,
        @SerialName("rating") val rating: Long,
        @SerialName("isAdditional") val isAdditional: Boolean,
    )

    @Serializable
    data class Leg(
        @SerialName("stopSequence") val stopSequence: List<StopSequenceClass>,
        @SerialName("distance") val distance: Long,
        @SerialName("hints") val hints: List<Hint>,
        @SerialName("origin") val origin: StopSequenceClass,
        @SerialName("destination") val destination: StopSequenceClass,
        @SerialName("pathDescriptions") val pathDescriptions: List<PathDescription>,
        @SerialName("isRealtimeControlled") val isRealtimeControlled: Boolean,
        @SerialName("transportation") val transportation: Transportation,
        @SerialName("duration") val duration: Long,
        @SerialName("footPathInfo") val footPathInfo: List<FootPathInfo>,
        @SerialName("coords") val coords: List<List<Long>>,
        @SerialName("infos") val infos: List<Info>,
        @SerialName("interchange") val interchange: Interchange,
        @SerialName("properties") val properties: LegProperties,
    )

    @Serializable
    data class StopSequenceClass(
        @SerialName("parent") val parent: Parent,
        @SerialName("coord") val coord: List<Long>,
        @SerialName("arrivalTimePlanned") val arrivalTimePlanned: String,
        @SerialName("disassembledName") val disassembledName: String,
        @SerialName("arrivalTimeEstimated") val arrivalTimeEstimated: String,
        @SerialName("departureTimeEstimated") val departureTimeEstimated: String,
        @SerialName("name") val name: String,
        @SerialName("departureTimePlanned") val departureTimePlanned: String,
        @SerialName("id") val id: String,
        @SerialName("type") val type: String,
        @SerialName("properties") val properties: DestinationProperties,
    )

    @Serializable
    data class Parent(
        @SerialName("parent") val parent: String,
        @SerialName("disassembledName") val disassembledName: String,
        @SerialName("name") val name: String,
        @SerialName("id") val id: String,
        @SerialName("type") val type: String,
    )

    @Serializable
    data class DestinationProperties(
        @SerialName("wheelchairAccess") val wheelchairAccess: String,
        @SerialName("downloads") val downloads: List<Download>,
    )

    @Serializable
    data class Download(
        @SerialName("type") val type: String,
        @SerialName("url") val url: String,
    )

    @Serializable
    data class FootPathInfo(
        @SerialName("duration") val duration: Long,
        @SerialName("footPathElem") val footPathElem: List<FootPathElem>,
        @SerialName("position") val position: String,
    )

    @Serializable
    data class FootPathElem(
        @SerialName("level") val level: String,
        @SerialName("levelTo") val levelTo: Long,
        @SerialName("origin") val origin: FootPathElemDestination,
        @SerialName("destination") val destination: FootPathElemDestination,
        @SerialName("description") val description: String,
        @SerialName("type") val type: String,
    )

    @Serializable
    data class FootPathElemDestination(
        @SerialName("area") val area: Long,
        @SerialName("georef") val georef: String,
        @SerialName("location") val location: Location,
        @SerialName("platform") val platform: Long,
    )

    @Serializable
    data class Location(
        @SerialName("coord") val coord: List<Long>,
        @SerialName("id") val id: String,
        @SerialName("type") val type: String,
    )

    @Serializable
    data class Hint(
        @SerialName("infoText") val infoText: String,
    )

    @Serializable
    data class Info(
        @SerialName("timestamps") val timestamps: Timestamps,
        @SerialName("subtitle") val subtitle: String,
        @SerialName("urlText") val urlText: String,
        @SerialName("id") val id: String,
        @SerialName("priority") val priority: String,
        @SerialName("version") val version: Long,
        @SerialName("content") val content: String,
        @SerialName("url") val url: String,
    )

    @Serializable
    data class Timestamps(
        @SerialName("lastModification") val lastModification: String,
        @SerialName("availability") val availability: Ity,
        @SerialName("validity") val validity: List<Ity>,
        @SerialName("creation") val creation: String,
    )

    @Serializable
    data class Ity(
        @SerialName("from") val from: String,
        @SerialName("to") val to: String,
    )

    @Serializable
    data class Interchange(
        @SerialName("type") val type: Long,
        @SerialName("coords") val coords: List<List<Long>>,
        @SerialName("desc") val desc: String,
    )

    @Serializable
    data class PathDescription(
        @SerialName("cumDistance") val cumDistance: Long,
        @SerialName("distance") val distance: Long,
        @SerialName("turnDirection") val turnDirection: String,
        @SerialName("distanceDown") val distanceDown: Long,
        @SerialName("fromCoordsIndex") val fromCoordsIndex: Long,
        @SerialName("cumDuration") val cumDuration: Long,
        @SerialName("distanceUp") val distanceUp: Long,
        @SerialName("duration") val duration: Long,
        @SerialName("coord") val coord: List<Long>,
        @SerialName("skyDirection") val skyDirection: Long,
        @SerialName("manoeuvre") val manoeuvre: String,
        @SerialName("toCoordsIndex") val toCoordsIndex: Long,
        @SerialName("name") val name: String,
    )

    @Serializable
    data class LegProperties(
        @SerialName("planLowFloorVehicle") val planLowFloorVehicle: String,
        @SerialName("lineType") val lineType: String,
        @SerialName("differentFares") val differentFares: String,
        @SerialName("planWheelChairAccess") val planWheelChairAccess: String,
        @SerialName("vehicleAccess") val vehicleAccess: List<String>,
    )

    @Serializable
    data class Transportation(
        @SerialName("iconId") val iconId: Long,
        @SerialName("number") val number: String,
        @SerialName("product") val product: Product,
        @SerialName("disassembledName") val disassembledName: String,
        @SerialName("destination") val destination: OperatorClass,
        @SerialName("name") val name: String,
        @SerialName("description") val description: String,
        @SerialName("id") val id: String,
        @SerialName("operator") val operator: OperatorClass,
        @SerialName("properties") val properties: TransportationProperties,
    )

    @Serializable
    data class OperatorClass(
        @SerialName("name") val name: String,
        @SerialName("id") val id: String,
    )

    @Serializable
    data class Product(
        @SerialName("iconID") val iconID: Long,
        @SerialName("name") val name: String,
        @SerialName("productClass") val productClass: Long,
    )

    @Serializable
    data class TransportationProperties(
        @SerialName("isTTB") val isTTB: Boolean,
        @SerialName("tripCode") val tripCode: Long,
    )

    @Serializable
    data class SystemMessages(
        @SerialName("responseMessages") val responseMessages: List<ResponseMessage>,
    )

    @Serializable
    data class ResponseMessage(
        @SerialName("code") val code: Long,
        @SerialName("module") val module: String,
        @SerialName("error") val error: String,
        @SerialName("type") val type: String,
    )
}
