package xyz.ksharma.krail.sydney.trains.database.real.parser

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import xyz.ksharma.krail.database.sydney.trains.database.api.TripsStore
import xyz.ksharma.krail.sydney.trains.database.Trip
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.nio.file.Path

object TripParser {

    suspend fun parseTrips(
        path: Path,
        ioDispatcher: CoroutineDispatcher,
        tripsStore: TripsStore,
    ) = withContext(ioDispatcher) {
        runCatching {
            val trips = mutableListOf<Trip>()
            val transactionBatchSize = 5_000

            BufferedReader(FileReader(path.toString())).use { reader ->
                val headersList = reader.readLine().split(",").trimQuotes()
                // todo use headers instead of hard code later.
                //Log.d(TAG, "headersList: $headersList")

                while (true) {
                    val line = reader.readLine() ?: break
                    val fieldsList = line.split(",").trimQuotes()

                    trips.add(
                        Trip(
                            route_id = fieldsList[0],
                            service_id = fieldsList[1],
                            trip_id = fieldsList[2],
                            trip_headsign = fieldsList[3],
                            trip_short_name = fieldsList[4],
                            direction_id = fieldsList[5].toLongOrNull(),
                            block_id = fieldsList[6],
                            shape_id = fieldsList[7],
                            wheelchair_accessible = fieldsList[8].toLongOrNull(),
                        )
                    )

                    // Insert in batches to improve performance
                    if (trips.size == transactionBatchSize) {
                        Timber.d("insert $transactionBatchSize trips")
                        tripsStore.insertTripsBatch(trips)
                        trips.clear() // Clear the batch
                    }
                }

                // Insert any remaining trips
                if (trips.isNotEmpty()) {
                    tripsStore.insertTripsBatch(trips)
                }
            }
        }.getOrElse { e ->
            Timber.e(e, "Error parseTrips: ")
        }
    }
}
