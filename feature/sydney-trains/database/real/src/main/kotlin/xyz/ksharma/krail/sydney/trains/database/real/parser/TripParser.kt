package xyz.ksharma.krail.sydney.trains.database.real.parser

import timber.log.Timber
import xyz.ksharma.krail.model.gtfs_static.Trip
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.nio.file.Path

object TripParser {

    internal fun Path.parseTrips(): List<Trip> {
        val trips = mutableListOf<Trip>()

        try {
            BufferedReader(FileReader(this.toString())).use { reader ->
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
                            direction_id = fieldsList[5].toIntOrNull(),
                            block_id =  fieldsList[6],
                            shape_id = fieldsList[7],
                            wheelchair_accessible = fieldsList[8].toIntOrNull(),
                        )
                    )
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Timber.e(e, "readStopsFromCSV: ")
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            Timber.e(e, "readStopsFromCSV: ")
        }

        return trips
    }
}
