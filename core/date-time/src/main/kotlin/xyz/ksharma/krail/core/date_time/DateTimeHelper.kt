package xyz.ksharma.krail.core.date_time

import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.toKotlinDuration

object DateTimeHelper {

    fun String.formatTo12HourTime(): String {
        // Parse the string as ZonedDateTime
        val zonedDateTime = ZonedDateTime.parse(this)

        // Define the formatter for 12-hour time with AM/PM
        val timeFormatter = DateTimeFormatter.ofPattern("h:mm a")

        // Format the ZonedDateTime to 12-hour format
        return zonedDateTime.format(timeFormatter)
    }

    /**
     * Convert a date-time string in UTC to AEST time zone.
     * E.g. "2021-08-01T12:00:00Z" -> "2021-08-01T22:00:00+10:00[Australia/Sydney]"
     */
    fun String.utcToAEST(): String {
        // Parse the string as a ZonedDateTime in UTC
        val utcDateTime = ZonedDateTime.parse(this, DateTimeFormatter.ISO_ZONED_DATE_TIME)

        // Convert to AEST time zone (UTC+10)
        val aestZoneId = ZoneId.of("Australia/Sydney")
        val aestDateTime = utcDateTime.withZoneSameInstant(aestZoneId)
        return aestDateTime.format(DateTimeFormatter.ISO_DATE_TIME)
    }

    /**
     * Converts a date-time string in Australian Eastern Standard Time (AEST)
     * to a string representing the time in HHMM format (24-hour format).
     *
     * The input string is expected to be in ISO 8601 format (e.g., "2024-09-24T19:00:00+10:00").
     *
     * @return The time in HHMM format (e.g., "1900").
     */
    fun String.aestToHHMM(): String {
        // Parse the AEST string as a ZonedDateTime
        val aestDateTime = ZonedDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)

        // Define the formatter for HHMM in 24-hour time format
        val timeFormatter = DateTimeFormatter.ofPattern("HHmm")

        // Format the ZonedDateTime to HHMM format
        return aestDateTime.format(timeFormatter)
    }

    /**
     * Calculates the time difference between two dates in the UTC format
     * ("2024-09-24T09:00:00Z")
     *
     * @param startDate The start date string.
     * @param endDate The end date string.
     *
     * @return The time difference between the two dates as a kotlin.time.Duration object.
     */
    fun calculateTimeDifference(startDate: String, endDate: String): kotlin.time.Duration {
        if (startDate.isBlank() || endDate.isBlank()) {
            throw IllegalArgumentException("Start and end dates cannot be empty or blank.")
        }

        val startDateTime = ZonedDateTime.parse(startDate)
        val endDateTime = ZonedDateTime.parse(endDate)

        if (endDateTime.isBefore(startDateTime)) {
            throw IllegalArgumentException("End date must be after start date.")
        }

        return Duration.between(startDateTime, endDateTime).toKotlinDuration()
    }

    /**
     * Converts a Kotlin Duration to a formatted string in the format "x mins" or "x h x mins".
     */
    fun kotlin.time.Duration.toFormattedString(): String {
        val hoursPart = inWholeHours.takeIf { it > 0 }?.let { "$it h " } ?: ""
        val minutesPart = inWholeMinutes % 60
        return "$hoursPart$minutesPart mins"
    }
}
