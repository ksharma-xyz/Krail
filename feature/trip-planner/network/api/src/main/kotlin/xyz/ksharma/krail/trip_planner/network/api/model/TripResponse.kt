package xyz.ksharma.krail.trip_planner.network.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TripResponse(
    @SerialName("systemMessages") val systemMessages: List<SystemMessages>? = null,
    @SerialName("journeys") val journeys: List<Journey>? = null,
    @SerialName("error") val error: Error? = null,
    @SerialName("version") val version: String? = null,
) {
    @Serializable
    data class Error(
        @SerialName("versions") val versions: Versions? = null,
        @SerialName("message") val message: String? = null,
    )

    @Serializable
    data class Versions(
        @SerialName("controller") val controller: String? = null,
        @SerialName("interfaceMax") val interfaceMax: String? = null,
        @SerialName("interfaceMin") val interfaceMin: String? = null,
    )

    @Serializable
    data class Journey(
        @SerialName("legs") val legs: List<Leg>? = null,
        @SerialName("rating") val rating: Long? = null,
        @SerialName("isAdditional") val isAdditional: Boolean? = null,
    )

    @Serializable
    data class Leg(
        @SerialName("stopSequence") val stopSequence: List<StopSequenceClass>? = null,
        @SerialName("distance") val distance: Long? = null,
        @SerialName("hints") val hints: List<Hint>? = null,
        @SerialName("origin") val origin: StopSequenceClass? = null,
        @SerialName("destination") val destination: StopSequenceClass? = null,
        @SerialName("pathDescriptions") val pathDescriptions: List<PathDescription>? = null,
        @SerialName("isRealtimeControlled") val isRealtimeControlled: Boolean? = null,
        @SerialName("transportation") val transportation: Transportation? = null,

        /**
         * In seconds
         */
        @SerialName("duration") val duration: Long? = null,
        @SerialName("footPathInfo") val footPathInfo: List<FootPathInfo>? = null,
        @SerialName("coords") val coords: List<List<Double>>? = null,
        @SerialName("infos") val infos: List<Info>? = null,
        @SerialName("interchange") val interchange: Interchange? = null,
        @SerialName("properties") val properties: LegProperties? = null,
    )

    @Serializable
    data class StopSequenceClass(
        @SerialName("parent") val parent: Parent? = null,
        @SerialName("coord") val coord: List<Double>? = null,
        @SerialName("arrivalTimePlanned") val arrivalTimePlanned: String? = null,
        @SerialName("disassembledName") val disassembledName: String? = null,
        @SerialName("arrivalTimeEstimated") val arrivalTimeEstimated: String? = null,
        @SerialName("departureTimeEstimated") val departureTimeEstimated: String? = null,
        @SerialName("name") val name: String? = null,
        @SerialName("departureTimePlanned") val departureTimePlanned: String? = null,
        @SerialName("id") val id: String? = null,
        @SerialName("type") val type: String? = null,
        @SerialName("properties") val properties: DestinationProperties? = null,
    )

    @Serializable
    data class Parent(
        @SerialName("parent") val parent: Parent? = null,
        @SerialName("disassembledName") val disassembledName: String? = null,
        @SerialName("name") val name: String? = null,
        @SerialName("id") val id: String? = null,
        @SerialName("type") val type: String? = null,
    )

    @Serializable
    data class DestinationProperties(
        @SerialName("wheelchairAccess") val wheelchairAccess: String? = null,
        @SerialName("downloads") val downloads: List<Download>? = null,
        @SerialName("NumberOfCars") val numberOfCars: String? = null,
        @SerialName("TravelInCarsFrom") val travelInCarsFrom: String? = null,
        @SerialName("TravelInCarsTo") val travelInCarsTo: String? = null,
        @SerialName("TravelInCarsMessage") val travelInCarsMessage: String? = null,
        @SerialName("occupancy") val occupancy: String? = null,
    )

    @Serializable
    data class Download(
        @SerialName("type") val type: String? = null,
        @SerialName("url") val url: String? = null,
    )

    @Serializable
    data class FootPathInfo(
        @SerialName("duration") val duration: Long? = null,
        @SerialName("footPathElem") val footPathElem: List<FootPathElem>? = null,
        @SerialName("position") val position: String? = null,
    )

    @Serializable
    data class FootPathElem(
        @SerialName("level") val level: String? = null,
        @SerialName("levelTo") val levelTo: Long? = null,
        @SerialName("origin") val origin: FootPathElemDestination? = null,
        @SerialName("destination") val destination: FootPathElemDestination? = null,
        @SerialName("description") val description: String? = null,
        @SerialName("type") val type: String? = null,
    )

    @Serializable
    data class FootPathElemDestination(
        @SerialName("area") val area: Long? = null,
        @SerialName("georef") val georef: String? = null,
        @SerialName("location") val location: Location? = null,
        @SerialName("platform") val platform: Long? = null,
    )

    @Serializable
    data class Location(
        @SerialName("coord") val coord: List<Double>? = null,
        @SerialName("id") val id: String? = null,
        @SerialName("type") val type: String? = null,
    )

    @Serializable
    data class Hint(
        @SerialName("infoText") val infoText: String? = null,
    )

    @Serializable
    data class Info(
        @SerialName("timestamps") val timestamps: Timestamps? = null,
        @SerialName("subtitle") val subtitle: String? = null,
        @SerialName("urlText") val urlText: String? = null,
        @SerialName("id") val id: String? = null,
        @SerialName("priority") val priority: String? = null,
        @SerialName("version") val version: Long? = null,
        @SerialName("content") val content: String? = null,
        @SerialName("url") val url: String? = null,
    )

    @Serializable
    data class Timestamps(
        @SerialName("lastModification") val lastModification: String? = null,
        @SerialName("availability") val availability: Ity? = null,
        @SerialName("validity") val validity: List<Ity>? = null,
        @SerialName("creation") val creation: String? = null,
    )

    @Serializable
    data class Ity(
        @SerialName("from") val from: String? = null,
        @SerialName("to") val to: String? = null,
    )

    @Serializable
    data class Interchange(
        @SerialName("type") val type: Long? = null,
        @SerialName("coords") val coords: List<List<Double>>? = null,
        @SerialName("desc") val desc: String? = null,
    )

    @Serializable
    data class PathDescription(
        @SerialName("cumDistance") val cumDistance: Long? = null,
        @SerialName("distance") val distance: Long? = null,
        @SerialName("turnDirection") val turnDirection: String? = null,
        @SerialName("distanceDown") val distanceDown: Long? = null,
        @SerialName("fromCoordsIndex") val fromCoordsIndex: Long? = null,
        @SerialName("cumDuration") val cumDuration: Long? = null,
        @SerialName("distanceUp") val distanceUp: Long? = null,
        @SerialName("duration") val duration: Long? = null,
        @SerialName("coord") val coord: List<Double>? = null,
        @SerialName("skyDirection") val skyDirection: Long? = null,
        @SerialName("manoeuvre") val manoeuvre: String? = null,
        @SerialName("toCoordsIndex") val toCoordsIndex: Long? = null,
        @SerialName("name") val name: String? = null,
    )

    @Serializable
    data class LegProperties(
        @SerialName("planLowFloorVehicle") val planLowFloorVehicle: String? = null,
        @SerialName("lineType") val lineType: String? = null,
        @SerialName("differentFares") val differentFares: String? = null,
        @SerialName("planWheelChairAccess") val planWheelChairAccess: String? = null,
    )

    @Serializable
    data class Transportation(
        @SerialName("iconId") val iconId: Long? = null,
        @SerialName("number") val number: String? = null,
        @SerialName("product") val product: Product? = null,
        @SerialName("disassembledName") val disassembledName: String? = null,
        @SerialName("destination") val destination: OperatorClass? = null,
        @SerialName("name") val name: String? = null,
        @SerialName("description") val description: String? = null,
        @SerialName("id") val id: String? = null,
        @SerialName("operator") val operator: OperatorClass? = null,
        @SerialName("properties") val properties: TransportationProperties? = null,
    )

    @Serializable
    data class OperatorClass(
        @SerialName("name") val name: String? = null,
        @SerialName("id") val id: String? = null,
    )

    @Serializable
    data class Product(
        @SerialName("iconID") val iconID: Long? = null,
        @SerialName("name") val name: String? = null,
        @SerialName("productClass") val productClass: Long? = null,
    )

    @Serializable
    data class TransportationProperties(
        @SerialName("isTTB") val isTTB: Boolean? = null,
        @SerialName("tripCode") val tripCode: Long? = null,
    )

    @Serializable
    data class SystemMessages(
        @SerialName("responseMessages") val responseMessages: List<ResponseMessage>? = null,
    )

    @Serializable
    data class ResponseMessage(
        @SerialName("code") val code: Long? = null,
        @SerialName("module") val module: String? = null,
        @SerialName("error") val error: String? = null,
        @SerialName("type") val type: String? = null,
    )
}
