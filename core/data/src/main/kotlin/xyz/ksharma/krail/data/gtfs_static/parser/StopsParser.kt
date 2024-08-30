package xyz.ksharma.krail.data.gtfs_static.parser

import timber.log.Timber
import xyz.ksharma.krail.model.gtfs_static.Stop
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.nio.file.Path

object StopsParser {

    internal fun Path.parseStops(): List<Stop> {
        val stops = mutableListOf<Stop>()

        try {
            BufferedReader(FileReader(this.toString())).use { reader ->
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
                            stop_lat = fieldsList[4],
                            stop_lon = fieldsList[5],
                            zone_id =  fieldsList[6],
                            stop_url = fieldsList[7],
                            location_type = fieldsList[8],
                            parent_station = fieldsList[9],
                            stop_timezone = fieldsList[10],
                            wheelchair_boarding = fieldsList[11].toInt().toWheelchairBoarding(),
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

        return stops
    }

    private fun Int.toWheelchairBoarding() = when (this) {
        0 -> xyz.ksharma.krail.model.gtfs_realtime.proto.Stop.WheelchairBoarding.UNKNOWN
        1 -> xyz.ksharma.krail.model.gtfs_realtime.proto.Stop.WheelchairBoarding.AVAILABLE
        2 -> xyz.ksharma.krail.model.gtfs_realtime.proto.Stop.WheelchairBoarding.NOT_AVAILABLE
        else -> null
    }
}
