package xyz.ksharma.krail.trip.planner.network.api.service.stop_finder

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import xyz.ksharma.krail.trip.planner.network.api.model.StopType
import xyz.ksharma.krail.trip.planner.network.api.model.TripResponse
import xyz.ksharma.krail.trip.planner.network.api.service.NSW_TRANSPORT_BASE_URL

suspend fun fetchStop(
    httpClient: HttpClient,
    apiKey: String,
    stopType: StopType,
    stopSearchQuery: String,
): TripResponse = withContext(Dispatchers.IO) {
    httpClient.get("${NSW_TRANSPORT_BASE_URL}/v1/tp/stop_finder") {
        headers {
            append("Authorization", "apikey $apiKey") // Example of Authorization header
            append(HttpHeaders.Accept, "application/json") // Accept JSON response
        }

        url {
            parameters.append(StopFinderRequestParams.nameSf, stopSearchQuery)

            parameters.append(StopFinderRequestParams.typeSf, stopType.type)
            parameters.append(StopFinderRequestParams.coordOutputFormat, "EPSG:4326")
            parameters.append(StopFinderRequestParams.outputFormat, "rapidJSON")
            parameters.append(StopFinderRequestParams.version, "10.2.1.42")
            parameters.append(StopFinderRequestParams.tfNSWSF, "true")
        }
    }.body()
}
