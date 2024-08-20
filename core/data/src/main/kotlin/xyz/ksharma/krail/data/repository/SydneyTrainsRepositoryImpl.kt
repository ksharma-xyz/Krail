package xyz.ksharma.krail.data.repository

import android.content.Context
import android.util.Log
import xyz.ksharma.krail.model.gtfs_realtime.proto.Stop
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher
import xyz.ksharma.krail.network.SydneyTrainsService
import javax.inject.Inject
import xyz.ksharma.krail.model.gtfs_realtime.proto.TranslatedString
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import kotlin.text.Charsets.UTF_8

class SydneyTrainsRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val sydneyTrainsService: SydneyTrainsService,
) : SydneyTrainsRepository {

    private val TAG = "RealTimeDataRepositoryI"

    override suspend fun getSydneyTrains() {
        Log.d(TAG, "getSydneyTrains: ")
        val sydneyTrainsResponse: ByteArray = sydneyTrainsService.fetchSydneyTrains() // Zip
        //println(sydneyTrainsResponse.toString(UTF_8))

        //val files = extractGtfsFiles(sydneyTrainsResponse)
        //Log.d(TAG, "files[${files.size}]: ${files.keys}")
        //Log.d(TAG, "file[1]: ${files.values.first()}")

        /*
                val stopsFile:ByteArray? = files.filter { it.key == "stops.txt" }.values.firstOrNull()
                Log.d(TAG, "stopsFile: ${stopsFile?.decodeToString()}")
                Log.d(TAG, "parse stopsFile: ${stopsFile?.parseStops()}")
        */
    }

    private fun extractGtfsFiles(zipData: ByteArray): Map<String, ByteArray> {
        val zipInputStream = ZipInputStream(ByteArrayInputStream(zipData))
        val files = mutableMapOf<String, ByteArray>()

        var entry: ZipEntry? = zipInputStream.nextEntry
        while (entry != null) {
            if (!entry.isDirectory) {
                val byteArrayOutputStream = ByteArrayOutputStream()
                val buffer = ByteArray(8192) // Adjust buffer size if needed
                var bytesRead: Int

                while (zipInputStream.read(buffer).also { bytesRead = it } != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead)
                }

                files[entry.name] = byteArrayOutputStream.toByteArray()
                byteArrayOutputStream.close()
            }
            entry = zipInputStream.nextEntry
        }
        zipInputStream.close()

        return files
    }

    private fun ByteArray.parseStops(): List<Stop> {
        val stops = mutableListOf<Stop>()
        val reader = BufferedReader(InputStreamReader(inputStream()))
        val lineList = reader.readLine()?.split(",") ?: return emptyList()
        Log.d(TAG, "parseStops: rows - ${lineList.size}")

        val columnIndices = lineList.mapIndexed { index, columnName -> columnName to index }.toMap()

        var line: String? = reader.readLine()
        Log.d(TAG, "parseStops: line - $line")

        while (line != null) {
            val tokens = line.split(",")

            val stop = Stop(
                stop_id = tokens.getOrNull(columnIndices["stop_id"] ?: -1) ?: "",
                stop_code = tokens.getOrNull(columnIndices["stop_code"] ?: -1)?.translate(),
                stop_name = tokens.getOrNull(columnIndices["stop_name"] ?: -1)?.translate(),
                tts_stop_name = tokens.getOrNull(columnIndices["tts_stop_name"] ?: -1)?.translate(),
                stop_desc = tokens.getOrNull(columnIndices["stop_desc"] ?: -1)?.translate(),
                stop_lat = tokens.getOrNull(columnIndices["stop_lat"] ?: -1)?.toFloatOrNull(),
                stop_lon = tokens.getOrNull(columnIndices["stop_lon"] ?: -1)?.toFloatOrNull(),
                zone_id = tokens.getOrNull(columnIndices["zone_id"] ?: -1),
                stop_url = tokens.getOrNull(columnIndices["stop_url"] ?: -1)?.translate(),
                parent_station = tokens.getOrNull(columnIndices["parent_station"] ?: -1),
                stop_timezone = tokens.getOrNull(columnIndices["stop_timezone"] ?: -1),
                wheelchair_boarding = tokens.getOrNull(columnIndices["wheelchair_boarding"] ?: -1)
                    ?.toIntOrNull().toWheelchairBoarding(),
                level_id = tokens.getOrNull(columnIndices["level_id"] ?: -1),
                platform_code = tokens.getOrNull(columnIndices["platform_code"] ?: -1)?.translate(),
            )
            stops.add(stop)
            line = reader.readLine()
        }

        return stops
    }

    private fun String.translate(): TranslatedString {
        // Create a Translation object with the text and language
        val translation = TranslatedString.Translation(
            text = this,
            language = "en"
        )

        // Create and return the TranslatedString object containing the translation
        return TranslatedString(translation = listOf(translation))
    }
}

private fun Int?.toWheelchairBoarding() = when (this) {
    0 -> Stop.WheelchairBoarding.UNKNOWN
    1 -> Stop.WheelchairBoarding.AVAILABLE
    2 -> Stop.WheelchairBoarding.NOT_AVAILABLE
    else -> null
}
