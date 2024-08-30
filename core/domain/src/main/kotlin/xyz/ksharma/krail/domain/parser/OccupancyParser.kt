package xyz.ksharma.krail.domain.parser

import timber.log.Timber
import xyz.ksharma.krail.model.gtfs_static.Occupancy
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.nio.file.Path

object OccupancyParser {

    fun Path.parseOccupancy(): List<Occupancy> {
        val occupancyList = mutableListOf<Occupancy>()

        try {
            BufferedReader(FileReader(this.toString())).use { reader ->
                val headersList = reader.readLine().split(",").trimQuotes()
                // todo use headers instead of hard code later.
                //Log.d(TAG, "headersList: $headersList")

                var line: String?

                while (true) {
                    line = reader.readLine() ?: break

                    // Process the line in a buffered manner
                    val fieldsList = mutableListOf<String>()
                    var currentField = ""
                    var inQuotes = false

                    for (char in line) {
                        if (inQuotes) {
                            if (char == '"') {
                                inQuotes = false
                                fieldsList.add(currentField)
                                currentField = ""
                            } else {
                                currentField += char
                            }
                        } else {
                            if (char == '"') {
                                inQuotes = true
                            } else if (char == ',') {
                                fieldsList.add(currentField.trim('\"'))
                                currentField = ""
                            } else {
                                currentField += char
                            }
                        }
                    }

                    fieldsList.add(currentField.trim('\"'))

                    occupancyList.add(
                        Occupancy(
                            tripId = fieldsList[0],
                            stopSequence = fieldsList[1],
                            occupancyStatus = fieldsList[2],
                            monday = fieldsList[3],
                            tuesday = fieldsList[4],
                            wednesday = fieldsList[5],
                            thursday = fieldsList[6],
                            friday = fieldsList[7],
                            saturday = fieldsList[8],
                            sunday = fieldsList[9],
                            startDate = fieldsList[10],
                            endDate = fieldsList[11],
                            exception = fieldsList[11],
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

        return occupancyList
    }
}
