package xyz.ksharma.krail.trip.planner.network.api.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import xyz.ksharma.krail.core.di.DispatchersComponent
import xyz.ksharma.krail.trip.planner.network.api.model.StopFinderResponse
import xyz.ksharma.krail.trip.planner.network.api.model.StopType
import xyz.ksharma.krail.trip.planner.network.api.model.TripResponse
import xyz.ksharma.krail.trip.planner.network.api.service.stop_finder.StopFinderRequestParams
import xyz.ksharma.krail.trip.planner.network.api.service.trip.TripRequestParams

class RealTripPlanningService(
    private val httpClient: HttpClient,
    private val ioDispatcher: CoroutineDispatcher,
) : TripPlanningService {

    override suspend fun trip(
        originStopId: String,
        destinationStopId: String,
        depArr: DepArr,
        date: String?,
        time: String?,
    ): TripResponse = withContext(ioDispatcher) {

        httpClient.get("$NSW_TRANSPORT_BASE_URL/v1/tp/trip") {
            url {
                parameters.append(TripRequestParams.nameOrigin, originStopId)
                parameters.append(TripRequestParams.nameDestination, destinationStopId)

                parameters.append(TripRequestParams.depArrMacro, depArr.macro)
                date?.let { parameters.append(TripRequestParams.itdDate, date) }
                time?.let { parameters.append(TripRequestParams.itdTime, time) }

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

    override suspend fun stopFinder(
        stopSearchQuery: String,
        stopType: StopType,
    ): StopFinderResponse = withContext(ioDispatcher) {
        httpClient.get("${NSW_TRANSPORT_BASE_URL}/v1/tp/stop_finder") {
            url {
                parameters.append(StopFinderRequestParams.nameSf, stopSearchQuery)

                parameters.append(StopFinderRequestParams.typeSf, stopType.type)
                parameters.append(StopFinderRequestParams.coordOutputFormat, "EPSG:4326")
                parameters.append(StopFinderRequestParams.outputFormat, "rapidJSON")
//                parameters.append(StopFinderRequestParams.version, "10.2.1.42")
                parameters.append(StopFinderRequestParams.tfNSWSF, "true")
            }
        }.body()
    }
}

enum class DepArr(val macro: String) {
    DEP("dep"),
    ARR("arr")
}
