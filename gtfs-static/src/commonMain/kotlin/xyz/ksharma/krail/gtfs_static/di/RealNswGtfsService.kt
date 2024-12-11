package xyz.ksharma.krail.gtfs_static.di

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class RealNswGtfsService(private val httpClient: HttpClient) : NswGtfsService {

    override suspend fun getSydneyTrains() = withContext(Dispatchers.IO) {
        val response: HttpResponse =
            httpClient.get("$NSW_TRANSPORT_BASE_URL/$GTFS_SCHEDULE_V1/sydneytrains")
    }

    override suspend fun getSydneyMetro() {
        val response: HttpResponse =
            httpClient.get("$NSW_TRANSPORT_BASE_URL/$GTFS_SCHEDULE_V2/metro")
    }

    override suspend fun getLightRail() {
        val response: HttpResponse =
            httpClient.get("$NSW_TRANSPORT_BASE_URL/$GTFS_SCHEDULE_V1/lightrail")
    }

    override suspend fun getNswTrains() {
        val response: HttpResponse =
            httpClient.get("$NSW_TRANSPORT_BASE_URL/$GTFS_SCHEDULE_V1/nswtrains")
    }

    override suspend fun getSydneyFerries() {
        val response: HttpResponse =
            httpClient.get("$NSW_TRANSPORT_BASE_URL/$GTFS_SCHEDULE_V1/ferries/sydneyferries")
    }

    companion object {
        internal const val NSW_TRANSPORT_BASE_URL = "https://api.transport.nsw.gov.au"
        internal const val GTFS_SCHEDULE_V1 = "v1/gtfs/schedule"
        internal const val GTFS_SCHEDULE_V2 = "v2/gtfs/schedule"
    }
}
