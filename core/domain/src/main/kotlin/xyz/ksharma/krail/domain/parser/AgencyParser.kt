package xyz.ksharma.krail.domain.parser

import timber.log.Timber
import xyz.ksharma.krail.model.gtfs_static.Agency
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.nio.file.Path

object AgencyParser {

    internal fun Path.parseAgency(): List<Agency> {
        val agencyList = mutableListOf<Agency>()

        try {
            BufferedReader(FileReader(this.toString())).use { reader ->
                val headersList = reader.readLine().split(",").trimQuotes()
                // todo use headers instead of hard code later.
                //Log.d(TAG, "headersList: $headersList")

                while (true) {
                    val line = reader.readLine() ?: break
                    val fieldsList = line.split(",").trimQuotes()

                    agencyList.add(
                        Agency(
                            agencyId = fieldsList[0],
                            agencyName = fieldsList[1],
                            agencyUrl = fieldsList[2],
                            agencyTimezone = fieldsList[3],
                            agencyLang = fieldsList[4],
                            agencyPhone = fieldsList[5],
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

        return agencyList
    }
}
