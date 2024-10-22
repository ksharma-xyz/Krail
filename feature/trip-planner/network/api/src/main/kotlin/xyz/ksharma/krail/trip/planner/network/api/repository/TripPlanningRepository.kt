package xyz.ksharma.krail.trip.planner.network.api.repository

import xyz.ksharma.krail.trip.planner.network.api.model.StopFinderResponse
import xyz.ksharma.krail.trip.planner.network.api.model.StopType
import xyz.ksharma.krail.trip.planner.network.api.model.TripResponse
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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
    val formatter = DateTimeFormatter.ofPattern("HHMM").withZone(ZoneId.of("Australia/Sydney"))
    return formatter.format(this)
}
