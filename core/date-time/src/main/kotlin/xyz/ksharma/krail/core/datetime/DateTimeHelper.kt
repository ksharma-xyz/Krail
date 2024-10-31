package xyz.ksharma.krail.core.datetime

import kotlinx.datetime.Instant
import timber.log.Timber
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.absoluteValue
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit
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
     * to a string representing the time in hh:mm a format (12-hour format).
     *
     * The input string is expected to be in ISO 8601 format (e.g., "2024-09-24T19:00:00+10:00").
     *
     * @return The time in hh:mm a format (e.g., "07:00 am").
     */
    fun String.aestToHHMM(): String {
        val aestDateTime = ZonedDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
        val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
        return aestDateTime.format(timeFormatter)
    }

    /**
     * Calculates the time difference between two dates in the UTC format
     * ("2024-09-24T09:00:00Z")
     *
     * @return The time difference between the two dates as a kotlin.time.Duration object.
     */
    fun calculateTimeDifferenceFromNow(
        utcDateString: String,
        now: Instant = kotlinx.datetime.Clock.System.now(), // Get current Instant in UTC
    ): kotlin.time.Duration {
        val instant = Instant.parse(utcDateString) // Parse UTC string to Instant
        return instant - now
    }

    fun kotlin.time.Duration.toGenericFormattedTimeString(): String {
        val totalMinutes = this.toLong(DurationUnit.MINUTES)
        val hours = this.toLong(DurationUnit.HOURS)
        val partialMinutes = totalMinutes - (hours * 60.minutes.inWholeMinutes)

        val formattedDifference = when {
            totalMinutes < 0 -> "${totalMinutes.absoluteValue} mins ago"
            totalMinutes == 0L -> "Now"
            hours == 1L -> "In ${hours.absoluteValue}h ${partialMinutes.absoluteValue}m"
            hours >= 2 -> "In ${hours.absoluteValue}h"
            else -> "In ${totalMinutes.absoluteValue} ${if (totalMinutes.absoluteValue == 1L) "min" else "mins"}"
        }
        Timber.d("\t minutes: $partialMinutes, hours: $hours, formattedDifference: $formattedDifference -> originTime")
        return formattedDifference
    }

    fun kotlin.time.Duration.toFormattedDurationTimeString(): String {
        val totalMinutes = this.toLong(DurationUnit.MINUTES)
        val hours = this.toLong(DurationUnit.HOURS)
        val partialMinutes = totalMinutes - (hours * 60.minutes.inWholeMinutes)

        val formattedDifference = when {
            hours >= 1 -> "${hours.absoluteValue}h ${partialMinutes.absoluteValue}m"
            else -> "${totalMinutes.absoluteValue} ${if (totalMinutes.absoluteValue == 1L) "min" else "mins"}"
        }
        return formattedDifference
    }

    fun calculateTimeDifference(utcDateString1: String, utcDateString2: String): kotlin.time.Duration {
        // Parse the first UTC date string to a ZonedDateTime
        val dateTime1 = ZonedDateTime.parse(utcDateString1, DateTimeFormatter.ISO_ZONED_DATE_TIME)

        // Parse the second UTC date string to a ZonedDateTime
        val dateTime2 = ZonedDateTime.parse(utcDateString2, DateTimeFormatter.ISO_ZONED_DATE_TIME)

        // Calculate the duration between the two ZonedDateTime instances
        return Duration.between(dateTime1, dateTime2).toKotlinDuration()
    }
}
