package xyz.ksharma.krail.sydney.trains.database.real.parser

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import xyz.ksharma.krail.database.sydney.trains.database.api.StopTimesStore
import xyz.ksharma.krail.sydney.trains.database.StopTimes
import java.io.BufferedReader
import java.io.FileReader
import java.nio.file.Path

/**
 * Takes about 20 seconds to insert about a million records, which is roughly 120MB file.
 * TODO - improve this to less than 5 seconds.
 */
object StopTimesParser {

    suspend fun parseStopTimes(
        path: Path,
        ioDispatcher: CoroutineDispatcher,
        stopTimesStore: StopTimesStore,
    ): Unit = withContext(ioDispatcher) {
        runCatching {
            BufferedReader(FileReader(path.toString())).use { reader ->

                val headersList = reader.readLine().split(",").trimQuotes()
                Timber.d("headersList: $headersList")
                val stopTimesList = mutableListOf<StopTimes>()
                var line: String?
                val transactionBatchSize = 5_000

                while (true) {
                    line = reader.readLine() ?: break

                    val fieldsList = line.split(",").trimQuotes()

                    stopTimesList.add(
                        StopTimes(
                            trip_id = fieldsList[0],
                            arrival_time = fieldsList[1],
                            departure_time = fieldsList[2],
                            stop_id = fieldsList[3],
                            stop_sequence = fieldsList[4].toLong(),
                            stop_headsign = fieldsList[5],
                            pickup_type = fieldsList[6].toLong(),
                            drop_off_type = fieldsList[7].toLong(),
                        )
                    )

                    // Insert in batches to improve performance
                    if (stopTimesList.size == transactionBatchSize) {
                        // Timber.d("insert $transactionBatchSize stop times")
                        stopTimesStore.insertStopTimesBatch(stopTimesList)
                        stopTimesList.clear() // Clear the batch
                    }
                }

                // Insert any remaining stop times
                if (stopTimesList.isNotEmpty()) {
                    stopTimesStore.insertStopTimesBatch(stopTimesList)
                }
            }
        }.getOrElse { e -> Timber.e(e, "Error while parsing stop times") }
    }
}
