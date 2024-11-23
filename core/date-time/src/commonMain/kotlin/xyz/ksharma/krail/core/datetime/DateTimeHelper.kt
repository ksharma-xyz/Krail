package xyz.ksharma.krail.core.datetime

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.absoluteValue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

object DateTimeHelper {

    fun String.formatTo12HourTime(): String {
        val localDateTime = Instant.parse(this).toLocalDateTime(TimeZone.UTC)
        val hour = if (localDateTime.hour % 12 == 0) 12 else localDateTime.hour % 12 // Ensure 12-hour format
        val minute = localDateTime.minute.toString().padStart(2, '0')
        val amPm = if (localDateTime.hour < 12) "am" else "pm"
        return "$hour:$minute $amPm"
    }

    fun String.utcToAEST(): String {
        val instant = Instant.parse(this)
        val aestZone = TimeZone.of("Australia/Sydney")
        val localDateTime = instant.toLocalDateTime(aestZone)
        return localDateTime.toString()
    }

    fun String.utcToLocalDateTimeAEST(): LocalDateTime {
        val instant = Instant.parse(this)
        val aestZone = TimeZone.of("Australia/Sydney")
        val localDateTime = instant.toLocalDateTime(aestZone)
        return localDateTime
    }

    fun LocalDateTime.toHHMM(): String {
        val hour = if (this.hour % 12 == 0) 12 else this.hour % 12 // Ensure 12-hour format
        val minute = this.minute.toString().padStart(2, '0')
        val amPm = if (this.hour < 12) "AM" else "PM"
        return "$hour:$minute $amPm"
    }

   /* fun String.aestToHHMM(): String {
        val dateTimeString = if (this.length == 16) "$this:00" else this
        val localDateTime = Instant.parse(dateTimeString).toLocalDateTime(TimeZone.of("Australia/Sydney"))
        val hour = if (localDateTime.hour % 12 == 0) 12 else localDateTime.hour % 12 // Ensure 12-hour format
        val minute = localDateTime.minute.toString().padStart(2, '0')
        val amPm = if (localDateTime.hour < 12) "AM" else "PM"
        return "$hour:$minute $amPm"
    }*/

    fun calculateTimeDifferenceFromNow(
        utcDateString: String,
        now: Instant = Clock.System.now(),
    ): Duration {
        val instant = Instant.parse(utcDateString)
        return instant - now
    }

    fun Duration.toGenericFormattedTimeString(): String {
        val totalMinutes = this.toLong(DurationUnit.MINUTES)
        val hours = this.toLong(DurationUnit.HOURS)
        val partialMinutes = totalMinutes - (hours * 60.minutes.inWholeMinutes)

        return when {
            totalMinutes < 0 -> "${totalMinutes.absoluteValue} ${if (totalMinutes.absoluteValue == 1L) "min" else "mins"} ago"
            totalMinutes == 0L -> "Now"
            hours == 1L -> "in ${hours.absoluteValue}h ${partialMinutes.absoluteValue}m"
            hours >= 2 -> "in ${hours.absoluteValue}h"
            else -> "in ${totalMinutes.absoluteValue} ${if (totalMinutes.absoluteValue == 1L) "min" else "mins"}"
        }
    }

    fun Duration.toFormattedDurationTimeString(): String {
        val totalMinutes = this.toLong(DurationUnit.MINUTES)
        val hours = this.toLong(DurationUnit.HOURS)
        val partialMinutes = totalMinutes - (hours * 60.minutes.inWholeMinutes)

        return when {
            hours >= 1 && partialMinutes == 0L -> "${hours.absoluteValue}h"
            hours >= 1 -> "${hours.absoluteValue}h ${partialMinutes.absoluteValue}m"
            else -> "${totalMinutes.absoluteValue} ${if (totalMinutes.absoluteValue == 1L) "min" else "mins"}"
        }
    }

    fun calculateTimeDifference(
        utcDateString1: String,
        utcDateString2: String,
    ): Duration {
        val instant1 = Instant.parse(utcDateString1)
        val instant2 = Instant.parse(utcDateString2)
        return (instant1 - instant2).absoluteValue
    }
}
