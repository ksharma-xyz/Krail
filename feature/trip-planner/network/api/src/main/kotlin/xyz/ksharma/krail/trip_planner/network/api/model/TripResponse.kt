package xyz.ksharma.krail.trip_planner.network.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TripResponse(
    @SerialName("error") val error: ErrorResponse?,
    @SerialName("journeys") val journeys: List<Journey>?,
    @SerialName("systemMessages") val systemMessages: List<SystemMessages>?,
    @SerialName("version") val version: String?,
) {

    @Serializable
    data class ErrorResponse(
        @SerialName("message") val message: String?,
        @SerialName("versions") val versions: Versions?,
    )

    @Serializable
    data class Versions(
        @SerialName("controller") val controller: String?,
        @SerialName("interfaceMax") val interfaceMax: String?,
        @SerialName("interfaceMin") val interfaceMin: String?,
    )

    @Serializable
    data class Journey(
        @SerialName("isAdditional") val isAdditional: Boolean,
        @SerialName("legs") val legs: List<Leg>,
        @SerialName("rating") val rating: Int,
    )

    @Serializable
    data class Leg(
        @SerialName("coords") val coords: List<List<Double>>,
        @SerialName("destination") val destination: Destination,
        @SerialName("distance") val distance: Int,
        @SerialName("duration") val duration: Int,
        @SerialName("footPathInfo") val footPathInfo: List<FootPathInfo>?,
        @SerialName("hints") val hints: List<Hint>?,
        @SerialName("infos") val infos: List<Info>?,
        @SerialName("interchange") val interchange: Interchange?,
        @SerialName("isRealtimeControlled") val isRealtimeControlled: Boolean,
        @SerialName("origin") val origin: Destination,
        @SerialName("pathDescriptions") val pathDescriptions: List<PathDescription>?,
        @SerialName("properties") val properties: LegProperties?,
        @SerialName("stopSequence") val stopSequence: List<Stop>?,
        @SerialName("transportation") val transportation: Transportation?,
    )

    @Serializable
    data class Destination(
        @SerialName("arrivalTimeEstimated") val arrivalTimeEstimated: String?,
        @SerialName("arrivalTimePlanned") val arrivalTimePlanned: String?,
        @SerialName("coord") val coord: List<Double>,
        @SerialName("departureTimeEstimated") val departureTimeEstimated: String?,
        @SerialName("departureTimePlanned") val departureTimePlanned: String?,
        @SerialName("disassembledName") val disassembledName: String?,
        @SerialName("id") val id: String?,
        @SerialName("name") val name: String?,
        @SerialName("parent") val parent: Parent?,
        @SerialName("properties") val properties: DestinationProperties?,
        @SerialName("type") val type: String?,
    )

    @Serializable
    data class Parent(
        @SerialName("disassembledName") val disassembledName: String?,
        @SerialName("id") val id: String?,
        @SerialName("name") val name: String?,
        @SerialName("parent") val parent: String?,
        @SerialName("type") val type: String?,
    )

    @Serializable
    data class DestinationProperties(
        @SerialName("WheelchairAccess") val wheelchairAccess: String?,
        @SerialName("downloads") val downloads: List<Download>?,
    )

    @Serializable
    data class Download(
        @SerialName("type") val type: String?,
        @SerialName("url") val url: String?,
    )

    @Serializable
    data class FootPathInfo(
        @SerialName("duration") val duration: Int,
        @SerialName("footPathElem") val footPathElem: List<FootPathElem>,
        @SerialName("position") val position: String?,
    )

    @Serializable
    data class FootPathElem(
        @SerialName("description") val description: String?,
        @SerialName("destination") val destination: FootPathDestination?,
        @SerialName("level") val level: String?,
        @SerialName("levelFrom") val levelFrom: Int,
        @SerialName("levelTo") val levelTo: Int,
        @SerialName("origin") val origin: FootPathDestination?,
        @SerialName("type") val type: String?,
    )

    @Serializable
    data class FootPathDestination(
        @SerialName("area") val area: Int,
        @SerialName("georef") val georef: String?,
        @SerialName("location") val location: Location?,
        @SerialName("platform") val platform: Int,
    )

    @Serializable
    data class Location(
        @SerialName("coord") val coord: List<Double>,
        @SerialName("id") val id: String?,
        @SerialName("type") val type: String?,
    )

    @Serializable
    data class Hint(
        @SerialName("infoText") val infoText: String?,
    )

    @Serializable
    data class Info(
        @SerialName("content") val content: String?,
        @SerialName("id") val id: String?,
        @SerialName("priority") val priority: String?,
        @SerialName("subtitle") val subtitle: String?,
        @SerialName("timestamps") val timestamps: Timestamps?,
        @SerialName("url") val url: String?,
        @SerialName("urlText") val urlText: String?,
        @SerialName("version") val version: Int,
    )

    @Serializable
    data class Timestamps(
        @SerialName("availability") val availability: Availability?,
        @SerialName("creation") val creation: String?,
        @SerialName("lastModification") val lastModification: String?,
        @SerialName("validity") val validity: List<Validity>?,
    )

    @Serializable
    data class Availability(
        @SerialName("from") val from: String?,
        @SerialName("to") val to: String?,
    )

    @Serializable
    data class Validity(
        @SerialName("from") val from: String?,
        @SerialName("to") val to: String?,
    )

    @Serializable
    data class Interchange(
        @SerialName("coords") val coords: List<List<Double>>,
        @SerialName("desc") val desc: String?,
        @SerialName("type") val type: Int,
    )

    @Serializable
    data class PathDescription(
        @SerialName("coord") val coord: List<Double>,
        @SerialName("cumDistance") val cumDistance: Int,
        @SerialName("cumDuration") val cumDuration: Int,
        @SerialName("distance") val distance: Int,
        @SerialName("distanceDown") val distanceDown: Int,
        @SerialName("distanceUp") val distanceUp: Int,
        @SerialName("duration") val duration: Int,
        @SerialName("fromCoordsIndex") val fromCoordsIndex: Int,
        @SerialName("manoeuvre") val manoeuvre: String?,
        @SerialName("name") val name: String?,
        @SerialName("skyDirection") val skyDirection: Int,
        @SerialName("toCoordsIndex") val toCoordsIndex: Int,
        @SerialName("turnDirection") val turnDirection: String?,
    )

    @Serializable
    data class LegProperties(
        @SerialName("DIFFERENT_FARES") val differentFares: String?,
        @SerialName("PlanLowFloorVehicle") val planLowFloorVehicle: String?,
        @SerialName("PlanWheelChairAccess") val planWheelChairAccess: String?,
        @SerialName("lineType") val lineType: String?,
        @SerialName("vehicleAccess") val vehicleAccess: List<String>?,
    )

    @Serializable
    data class Stop(
        @SerialName("arrivalTimeEstimated") val arrivalTimeEstimated: String?,
        @SerialName("arrivalTimePlanned") val arrivalTimePlanned: String?,
        @SerialName("coord") val coord: List<Double>,
        @SerialName("departureTimeEstimated") val departureTimeEstimated: String?,
        @SerialName("departureTimePlanned") val departureTimePlanned: String?,
        @SerialName("disassembledName") val disassembledName: String?,
        @SerialName("id") val id: String?,
        @SerialName("name") val name: String?,
        @SerialName("parent") val parent: Parent?,
        @SerialName("properties") val properties: DestinationProperties?,
        @SerialName("type") val type: String?,
    )

    @Serializable
    data class Transportation(
        @SerialName("description") val description: String?,
        @SerialName("destination") val destination: DestinationInfo?,
        @SerialName("disassembledName") val disassembledName: String?,
        @SerialName("iconId") val iconId: Int,
        @SerialName("id") val id: String?,
        @SerialName("name") val name: String?,
        @SerialName("number") val number: String?,
        @SerialName("operator") val operator: Operator?,
        @SerialName("product") val product: Product?,
        @SerialName("properties") val properties: TransportationProperties?,
    )

    @Serializable
    data class DestinationInfo(
        @SerialName("id") val id: String?,
        @SerialName("name") val name: String?,
    )

    @Serializable
    data class Operator(
        @SerialName("id") val id: String?,
        @SerialName("name") val name: String?,
    )

    @Serializable
    data class Product(
        @SerialName("class") val clazz: Int,
        @SerialName("iconId") val iconId: Int,
        @SerialName("name") val name: String?,
    )

    @Serializable
    data class TransportationProperties(
        @SerialName("isTTB") val isTTB: Boolean,
        @SerialName("tripCode") val tripCode: Int,
    )

    @Serializable
    data class SystemMessages(
        @SerialName("responseMessages") val responseMessages: List<ResponseMessage>?,
    )

    @Serializable
    data class ResponseMessage(
        @SerialName("code") val code: Int,
        @SerialName("error") val error: String?,
        @SerialName("module") val module: String?,
        @SerialName("type") val type: String?,
    )
}
