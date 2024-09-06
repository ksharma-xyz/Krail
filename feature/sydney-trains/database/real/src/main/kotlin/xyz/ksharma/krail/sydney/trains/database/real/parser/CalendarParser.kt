package xyz.ksharma.krail.sydney.trains.database.real.parser

import timber.log.Timber
import xyz.ksharma.krail.model.gtfs_static.Calendar
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.nio.file.Path

object CalendarParser {

    internal fun Path.parseCalendar(): List<Calendar> {
        val calendarList = mutableListOf<Calendar>()

        try {
            BufferedReader(FileReader(this.toString())).use { reader ->
                val headersList = reader.readLine().split(",").trimQuotes()
                // todo use headers instead of hard code later.
                //Log.d(TAG, "headersList: $headersList")

                while (true) {
                    val line = reader.readLine() ?: break
                    val fieldsList = line.split(",").trimQuotes()

                    calendarList.add(
                        Calendar(
                            serviceId = fieldsList[0],
                            monday = fieldsList[1],
                            tuesday = fieldsList[2],
                            wednesday = fieldsList[3],
                            thursday = fieldsList[4],
                            friday = fieldsList[5],
                            saturday =  fieldsList[6],
                            sunday = fieldsList[7],
                            startDate = fieldsList[8],
                            endDate = fieldsList[9],
                        )
                    )
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Timber.e(e, "parseCalendar: ")
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            Timber.e(e, "parseCalendar: ")
        }

        return calendarList
    }
}
