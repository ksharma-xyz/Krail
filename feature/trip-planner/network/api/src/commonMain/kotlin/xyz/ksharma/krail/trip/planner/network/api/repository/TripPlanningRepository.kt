package xyz.ksharma.krail.trip.planner.network.api.repository

import xyz.ksharma.krail.trip.planner.network.api.model.StopFinderResponse
import xyz.ksharma.krail.trip.planner.network.api.model.StopType
import xyz.ksharma.krail.trip.planner.network.api.model.TripResponse
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

interface TripPlanningRepository {

    suspend fun stopFinder(
        stopType: StopType = StopType.STOP,
        stopSearchQuery: String,
    ): Result<StopFinderResponse>

    suspend fun trip(
        originStopId: String,
        destinationStopId: String,
        journeyTime: String? = null,
    ): Result<TripResponse>
}

/**
 * Converts Instant to ITD Time in HHMM 24-hour format.
 * // todo - move to another module for time related functions
 */
fun Instant.toItdTime(): String {
    val localDateTime = this.toLocalDateTime(TimeZone.of("Australia/Sydney"))
    return localDateTime.hour.toString().padStart(2, '0') + localDateTime.minute.toString().padStart(2, '0')
}
