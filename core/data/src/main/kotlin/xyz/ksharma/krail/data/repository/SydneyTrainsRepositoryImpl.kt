package xyz.ksharma.krail.data.repository

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.Response
import xyz.ksharma.krail.data.cacheZipResponse
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher
import xyz.ksharma.krail.model.gtfs_realtime.proto.Stop
import xyz.ksharma.krail.model.gtfs_realtime.proto.TranslatedString
import xyz.ksharma.krail.network.GtfsService
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

class SydneyTrainsRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val gtfsService: GtfsService,
) : SydneyTrainsRepository {

    private val TAG = "RealTimeDataRepositoryI"

    override suspend fun getSydneyTrains() {
        Log.d(TAG, "getSydneyTrains: ")
        val response: Response = gtfsService.getSydneyTrainSchedule()
        response.cacheZipResponse(dispatcher = ioDispatcher, context = context)
    }

    private fun ByteArray.parseStops(): List<Stop> {
        val stops = mutableListOf<Stop>()
        val reader = BufferedReader(InputStreamReader(inputStream()))
        val lineList = reader.readLine()?.split(",") ?: return emptyList()
        Log.d(TAG, "parseStops: rows - ${lineList.size}")

        val columnIndices =
            lineList.mapIndexed { index, columnName -> columnName to index }.toMap()

        var line: String? = reader.readLine()
        Log.d(TAG, "parseStops: line - $line")

        while (line != null) {
            val tokens = line.split(",")

            val stop = Stop(
                stop_id = tokens.getOrNull(columnIndices["stop_id"] ?: -1) ?: "",
                stop_code = tokens.getOrNull(columnIndices["stop_code"] ?: -1)?.translate(),
                stop_name = tokens.getOrNull(columnIndices["stop_name"] ?: -1)?.translate(),
                tts_stop_name = tokens.getOrNull(columnIndices["tts_stop_name"] ?: -1)
                    ?.translate(),
                stop_desc = tokens.getOrNull(columnIndices["stop_desc"] ?: -1)?.translate(),
                stop_lat = tokens.getOrNull(columnIndices["stop_lat"] ?: -1)?.toFloatOrNull(),
                stop_lon = tokens.getOrNull(columnIndices["stop_lon"] ?: -1)?.toFloatOrNull(),
                zone_id = tokens.getOrNull(columnIndices["zone_id"] ?: -1),
                stop_url = tokens.getOrNull(columnIndices["stop_url"] ?: -1)?.translate(),
                parent_station = tokens.getOrNull(columnIndices["parent_station"] ?: -1),
                stop_timezone = tokens.getOrNull(columnIndices["stop_timezone"] ?: -1),
                wheelchair_boarding = tokens.getOrNull(
                    columnIndices["wheelchair_boarding"] ?: -1
                )
                    ?.toIntOrNull().toWheelchairBoarding(),
                level_id = tokens.getOrNull(columnIndices["level_id"] ?: -1),
                platform_code = tokens.getOrNull(columnIndices["platform_code"] ?: -1)
                    ?.translate(),
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