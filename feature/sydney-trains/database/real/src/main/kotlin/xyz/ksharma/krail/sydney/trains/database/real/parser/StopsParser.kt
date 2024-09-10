package xyz.ksharma.krail.sydney.trains.database.real.parser

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import xyz.ksharma.krail.database.sydney.trains.database.api.StopsStore
import xyz.ksharma.krail.sydney.trains.database.Stop
import java.io.BufferedReader
import java.io.FileReader
import java.nio.file.Path

object StopsParser {

    suspend fun parseStops(
        path: Path,
        ioDispatcher: CoroutineDispatcher,
        stopsStore: StopsStore,
    ): Unit = withContext(ioDispatcher) {
        runCatching {
            val stops = mutableListOf<Stop>()
            val transactionBatchSize = 200

            BufferedReader(FileReader(path.toString())).use { reader ->
                val headersList = reader.readLine().split(",").trimQuotes()
                // todo use headers instead of hard code later.
                //Log.d(TAG, "headersList: $headersList")

                while (true) {
                    val line = reader.readLine() ?: break
                    val fieldsList = line.split(",").trimQuotes()

                    stops.add(
                        Stop(
                            stop_id = fieldsList[0],
                            stop_code = fieldsList[1],
                            stop_name = fieldsList[2],
                            stop_desc = fieldsList[3],
                            stop_lat = fieldsList[4].toDoubleOrNull(),
                            stop_lon = fieldsList[5].toDoubleOrNull(),
                            zone_id = fieldsList[6],
                            stop_url = fieldsList[7],
                            location_type = fieldsList[8].toLongOrNull(),
                            parent_station = fieldsList[9],
                            stop_timezone = fieldsList[10],
                            wheelchair_boarding = fieldsList[11].toLongOrNull(),
                        )
                    )

                    if (stops.size == transactionBatchSize) {
                        stopsStore.insertStopsBatch(stopsList = stops)
                        stops.clear()
                    }
                }

                // Insert remaining stops
                if (stops.isNotEmpty()) {
                    stopsStore.insertStopsBatch(stopsList = stops)
                    stops.clear()
                }
            }
        }.getOrElse { e ->
            Timber.e(e, "Error parseStops: ")
        }
    }

    private fun Int.toWheelchairBoarding() = when (this) {
        0 -> xyz.ksharma.krail.model.gtfs_realtime.proto.Stop.WheelchairBoarding.UNKNOWN
        1 -> xyz.ksharma.krail.model.gtfs_realtime.proto.Stop.WheelchairBoarding.AVAILABLE
        2 -> xyz.ksharma.krail.model.gtfs_realtime.proto.Stop.WheelchairBoarding.NOT_AVAILABLE
        else -> null
    }
}
