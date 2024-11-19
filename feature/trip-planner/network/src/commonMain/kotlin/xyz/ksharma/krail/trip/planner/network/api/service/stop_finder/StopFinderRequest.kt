package xyz.ksharma.krail.trip.planner.network.api.service.stop_finder

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import xyz.ksharma.krail.trip.planner.network.api.model.StopFinderResponse
import xyz.ksharma.krail.trip.planner.network.api.model.StopType
import xyz.ksharma.krail.trip.planner.network.api.service.NSW_TRANSPORT_BASE_URL

suspend fun fetchStop(
    httpClient: HttpClient,
    stopType: StopType = StopType.STOP,
    stopSearchQuery: String,
): StopFinderResponse {
    return httpClient.get("${NSW_TRANSPORT_BASE_URL}/v1/tp/stop_finder") {
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
