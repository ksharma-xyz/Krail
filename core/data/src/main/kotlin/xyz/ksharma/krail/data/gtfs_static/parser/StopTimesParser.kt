package xyz.ksharma.krail.data.gtfs_static.parser

import timber.log.Timber
import xyz.ksharma.krail.model.gtfs_static.StopTimes
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.nio.file.Path

object StopTimesParser {

    internal fun Path.parseStopTimes(): List<StopTimes> {
        val stopTimesList = mutableListOf<StopTimes>()

        try {
            BufferedReader(FileReader(this.toString())).use { reader ->
                val headersList = reader.readLine().split(",").trimQuotes()
                // todo use headers instead of hard code later.
                //Log.d(TAG, "headersList: $headersList")

                while (true) {
                    val line = reader.readLine() ?: break
                    val fieldsList = line.split(",").trimQuotes()

                    stopTimesList.add(
                        StopTimes(
                            trip_id = fieldsList[0],
                            arrival_time = fieldsList[1],
                            departure_time = fieldsList[2],
                            stop_id = fieldsList[3],
                            stop_sequence = fieldsList[4].toIntOrNull(),
                            stop_headsign = fieldsList[5],
                            pickup_type = fieldsList[6].toIntOrNull(),
                            drop_off_type = fieldsList[7].toIntOrNull(),
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

        return stopTimesList
    }

    private fun List<String>.trimQuotes(): List<String> = this.map { it.trim('\"') }

}
