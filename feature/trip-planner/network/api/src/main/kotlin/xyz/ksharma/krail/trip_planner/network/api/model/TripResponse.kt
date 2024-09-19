package xyz.ksharma.krail.trip_planner.network.api.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class TripResponse(
    @SerialName("") val systemMessages: SystemMessages,
    @SerialName("") val journeys: List<Journey>,
    @SerialName("") val error: Error,
    @SerialName("") val version: String,
) {
    @Serializable
    data class Error(
        @SerialName("") val versions: Versions,
        @SerialName("") val message: String,
    )

    @Serializable
    data class Versions(
        @SerialName("") val controller: String,
        @SerialName("") val interfaceMax: String,
        @SerialName("") val interfaceMin: String,
    )

    @Serializable
    data class Journey(
        @SerialName("") val legs: List<Leg>,
        @SerialName("") val rating: Long,
        @SerialName("") val isAdditional: Boolean,
    )

    @Serializable
    data class Leg(
        @SerialName("") val stopSequence: List<StopSequenceClass>,
        @SerialName("") val distance: Long,
        @SerialName("") val hints: List<Hint>,
        @SerialName("") val origin: StopSequenceClass,
        @SerialName("") val destination: StopSequenceClass,
        @SerialName("") val pathDescriptions: List<PathDescription>,
        @SerialName("") val isRealtimeControlled: Boolean,
        @SerialName("") val transportation: Transportation,
        @SerialName("") val duration: Long,
        @SerialName("") val footPathInfo: List<FootPathInfo>,
        @SerialName("") val coords: List<List<Long>>,
        @SerialName("") val infos: List<Info>,
        @SerialName("") val interchange: Interchange,
        @SerialName("") val properties: LegProperties,
    )

    @Serializable
    data class StopSequenceClass(
        @SerialName("") val parent: Parent,
        @SerialName("") val coord: List<Long>,
        @SerialName("") val arrivalTimePlanned: String,
        @SerialName("") val disassembledName: String,
        @SerialName("") val arrivalTimeEstimated: String,
        @SerialName("") val departureTimeEstimated: String,
        @SerialName("") val name: String,
        @SerialName("") val departureTimePlanned: String,
        @SerialName("") val id: String,
        @SerialName("") val type: String,
        @SerialName("") val properties: DestinationProperties,
    )

    @Serializable
    data class Parent(
        @SerialName("") val parent: String,
        @SerialName("") val disassembledName: String,
        @SerialName("") val name: String,
        @SerialName("") val id: String,
        @SerialName("") val type: String,
    )

    @Serializable
    data class DestinationProperties(
        @SerialName("") val wheelchairAccess: String,
        @SerialName("") val downloads: List<Download>,
    )

    @Serializable
    data class Download(
        @SerialName("") val type: String,
        @SerialName("") val url: String,
    )

    @Serializable
    data class FootPathInfo(
        @SerialName("") val duration: Long,
        @SerialName("") val footPathElem: List<FootPathElem>,
        @SerialName("") val position: String,
    )

    @Serializable
    data class FootPathElem(
        @SerialName("") val level: String,
        @SerialName("") val levelTo: Long,
        @SerialName("") val origin: FootPathElemDestination,
        @SerialName("") val destination: FootPathElemDestination,
        @SerialName("") val description: String,
        @SerialName("") val type: String,
        @SerialName("") val levelFrom: Long,
    )

    @Serializable
    data class FootPathElemDestination(
        @SerialName("") val area: Long,
        @SerialName("") val georef: String,
        @SerialName("") val location: Location,
        @SerialName("") val platform: Long,
    )

    @Serializable
    data class Location(
        @SerialName("") val coord: List<Long>,
        @SerialName("") val id: String,
        @SerialName("") val type: String,
    )

    @Serializable
    data class Hint(
        @SerialName("") val infoText: String,
    )

    @Serializable
    data class Info(
        @SerialName("") val timestamps: Timestamps,
        @SerialName("") val subtitle: String,
        @SerialName("") val urlText: String,
        @SerialName("") val id: String,
        @SerialName("") val priority: String,
        @SerialName("") val version: Long,
        @SerialName("") val content: String,
        @SerialName("") val url: String,
    )

    @Serializable
    data class Timestamps(
        @SerialName("") val lastModification: String,
        @SerialName("") val availability: Ity,
        @SerialName("") val validity: List<Ity>,
        @SerialName("") val creation: String,
    )

    @Serializable
    data class Ity(
        @SerialName("") val from: String,
        @SerialName("") val to: String,
    )

    @Serializable
    data class Interchange(
        @SerialName("") val type: Long,
        @SerialName("") val coords: List<List<Long>>,
        @SerialName("") val desc: String,
    )

    @Serializable
    data class PathDescription(
        @SerialName("") val cumDistance: Long,
        @SerialName("") val distance: Long,
        @SerialName("") val turnDirection: String,
        @SerialName("") val distanceDown: Long,
        @SerialName("") val fromCoordsIndex: Long,
        @SerialName("") val cumDuration: Long,
        @SerialName("") val distanceUp: Long,
        @SerialName("") val duration: Long,
        @SerialName("") val coord: List<Long>,
        @SerialName("") val skyDirection: Long,
        @SerialName("") val manoeuvre: String,
        @SerialName("") val toCoordsIndex: Long,
        @SerialName("") val name: String,
    )

    @Serializable
    data class LegProperties(
        @SerialName("") val planLowFloorVehicle: String,
        @SerialName("") val lineType: String,
        @SerialName("") val differentFares: String,
        @SerialName("") val planWheelChairAccess: String,
        @SerialName("") val vehicleAccess: List<String>,
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
