package xyz.ksharma.krail.trip.planner.network.api.service.trip

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import xyz.ksharma.krail.trip.planner.network.api.model.TripResponse
import xyz.ksharma.krail.trip.planner.network.api.service.NSW_TRANSPORT_BASE_URL

suspend fun fetchTrip(
    httpClient: HttpClient, apiKey: String,
    originStopId: String,
    destinationStopId: String,
): TripResponse = withContext(Dispatchers.IO) {
    httpClient.get("$NSW_TRANSPORT_BASE_URL/v1/tp/trip") {
        url {
            parameters.append(TripRequestParams.nameOrigin, originStopId)
            parameters.append(TripRequestParams.nameDestination, destinationStopId)

            parameters.append(TripRequestParams.depArrMacro, "dep")
            parameters.append(TripRequestParams.typeDestination, "any")
            parameters.append(TripRequestParams.calcNumberOfTrips, "6")
            parameters.append(TripRequestParams.typeOrigin, "any")
            parameters.append(TripRequestParams.tfNSWTR, "true")
            parameters.append(TripRequestParams.version, "10.2.1.42")
            parameters.append(TripRequestParams.coordOutputFormat, "EPSG:4326")
            parameters.append(TripRequestParams.itOptionsActive, "1")
            parameters.append(TripRequestParams.computeMonomodalTripBicycle, "false")
            parameters.append(TripRequestParams.cycleSpeed, "16")
            parameters.append(TripRequestParams.useElevationData, "1")
            parameters.append(TripRequestParams.outputFormat, "rapidJSON")
        }
    }.body()
}

/**
 * Converts Instant to ITD Time in HHMM 24-hour format.
 * // todo - move to another module for time related functions
 */
fun Instant.toItdTime(): String {
    val localDateTime = this.toLocalDateTime(TimeZone.of("Australia/Sydney"))
    return localDateTime.hour.toString().padStart(2, '0') + localDateTime.minute.toString()
        .padStart(2, '0')
}
