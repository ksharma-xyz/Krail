package xyz.ksharma.krail.database.sydney.trains.database.api

import xyz.ksharma.krail.sydney.trains.database.StopTimes

interface SydneyTrainsStaticDB {

    suspend fun insertStopTimes(
        tripId: String,
        arrivalTime: String,
        departureTime: String,
        stopId: String,
        stopSequence: Int?,
        stopHeadsign: String,
        pickupType: Int?,
        dropOffType: Int?
    )

    suspend fun clearStopTimes()

    suspend fun getStopTimes(): List<StopTimes>

    suspend fun insertStopTimesBatch(stopTimesList: List<StopTimes>)

    suspend fun stopTimesSize(): Long
}
