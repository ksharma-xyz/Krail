package xyz.ksharma.krail.data.gtfs_static.parser


import timber.log.Timber
import xyz.ksharma.krail.model.gtfs_static.Route
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.nio.file.Path

object RouteParser {

    fun Path.parseRoutes(): List<Route> {
        val routesList = mutableListOf<Route>()

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

                    routesList.add(
                        Route(
                            routeId = fieldsList[0],
                            agencyId = fieldsList[1],
                            routeShortName = fieldsList[2],
                            routeLongName = fieldsList[3],
                            routeDesc = fieldsList[4],
                            routeType = fieldsList[5].toIntOrNull(),
                            routeUrl = fieldsList[6],
                            routeColor = fieldsList[7],
                            routeTextColor = fieldsList[8],
                        )
                    )
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Timber.e(e, "parseRoutes: ")
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            Timber.e(e, "parseRoutes: ")
        }
        return routesList
    }
}
