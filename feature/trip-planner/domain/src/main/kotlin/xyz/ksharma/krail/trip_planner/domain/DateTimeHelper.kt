package xyz.ksharma.krail.trip_planner.domain

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object DateTimeHelper {

    fun String.formatTo12HourTime(): String {
        // Parse the string as ZonedDateTime
        val zonedDateTime = ZonedDateTime.parse(this)

        // Define the formatter for 12-hour time with AM/PM
        val timeFormatter = DateTimeFormatter.ofPattern("h:mm a")

        // Format the ZonedDateTime to 12-hour format
        return zonedDateTime.format(timeFormatter)
    }


    fun String.utcToAEST(): String {
        // Parse the string as a ZonedDateTime in UTC
        val utcDateTime = ZonedDateTime.parse(this, DateTimeFormatter.ISO_ZONED_DATE_TIME)

        // Convert to AEST time zone (UTC+10)
        val aestZoneId = ZoneId.of("Australia/Sydney")
        val aestDateTime = utcDateTime.withZoneSameInstant(aestZoneId)
        return aestDateTime.format(DateTimeFormatter.ISO_DATE_TIME)
    }

    // New function to convert a date-time string in AEST to HHMM (24-hour format)
    fun String.aestToHHMM(): String {
        // Parse the AEST string as a ZonedDateTime
        val aestDateTime = ZonedDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)

        // Define the formatter for HHMM in 24-hour time format
        val timeFormatter = DateTimeFormatter.ofPattern("HHmm")

        // Format the ZonedDateTime to HHMM format
        return aestDateTime.format(timeFormatter)
    }
}
