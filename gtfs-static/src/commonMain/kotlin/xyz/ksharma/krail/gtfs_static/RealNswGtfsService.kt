package xyz.ksharma.krail.gtfs_static

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readRawBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import xyz.ksharma.krail.core.log.log

class RealNswGtfsService(
    private val httpClient: HttpClient,
    private val fileStorage: FileStorage,
) : NswGtfsService {

    override suspend fun getSydneyTrains() = withContext(Dispatchers.IO) {
        val response: HttpResponse =
            httpClient.get("$NSW_TRANSPORT_BASE_URL/$GTFS_SCHEDULE_V1/sydneytrains")

        if (response.status.value == 200) {
            log("Downloading file: ")
            val data = response.readRawBytes()
            fileStorage.saveFile("sydneytrains.zip", data)
            log("File downloaded")
        } else {
            throw Exception("Failed to download file: ${response.status}")
        }
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
