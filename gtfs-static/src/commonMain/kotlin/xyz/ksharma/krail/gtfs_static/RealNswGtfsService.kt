package xyz.ksharma.krail.gtfs_static

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readRawBytes
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okio.Path
import xyz.ksharma.krail.core.di.DispatchersComponent
import xyz.ksharma.krail.core.io.FileStorage
import xyz.ksharma.krail.core.io.ZipFileManager
import xyz.ksharma.krail.core.io.fileSystem
import xyz.ksharma.krail.core.log.log

internal class RealNswGtfsService(
    private val httpClient: HttpClient,
    private val fileStorage: FileStorage,
    private val zipManager: ZipFileManager,
    private val ioDispatcher: CoroutineDispatcher = DispatchersComponent().ioDispatcher,
    private val coroutineScope: CoroutineScope,
) : NswGtfsService {

    override suspend fun getSydneyTrains() {
        coroutineScope.launch(ioDispatcher) {
            val response: HttpResponse =
                httpClient.get("$NSW_TRANSPORT_BASE_URL/$GTFS_SCHEDULE_V1/sydneytrains")

            if (response.isSuccessful()) {
                log("Downloading file: ")
                val data = response.readRawBytes()
                val path: Path = fileStorage.saveFile("sydneytrains.zip", data)
                log("File downloaded at: $path")

                // Unzip the file
                zipManager.unzip(path)
            } else {
                throw Exception("Failed to download file: ${response.status}")
            }
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

    private fun HttpResponse.isSuccessful() = status.value == HttpStatusCode.OK.value

    companion object {
        internal const val NSW_TRANSPORT_BASE_URL = "https://api.transport.nsw.gov.au"
        internal const val GTFS_SCHEDULE_V1 = "v1/gtfs/schedule"
        internal const val GTFS_SCHEDULE_V2 = "v2/gtfs/schedule"
    }
}
