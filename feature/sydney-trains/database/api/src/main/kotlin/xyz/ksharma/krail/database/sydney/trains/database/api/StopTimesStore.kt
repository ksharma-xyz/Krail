package xyz.ksharma.krail.database.sydney.trains.database.api

import xyz.ksharma.krail.sydney.trains.database.StopTimes

/**
 * Represents a local data source for managing stop_times.txt data received from the
 * Sydney Trains static GTFS data.
 *
 * It represents table "stopTimes" in the database and will store and query data from SQLite db.
 **/
interface StopTimesStore {

    /**
     * Adds a new stop time to the database.
     *
     * @param tripId The unique identifier for the trip. e.g. 1--A.1904.101.130.B.8.81035887
     * @param arrivalTime The scheduled arrival time at the stop. e.g. 04:23:06
     * @param departureTime The scheduled departure time from the stop. e.g. 04:23:06
     * @param stopId The unique identifier for the stop. e.g. 2142324
     * @param stopSequence The order of the stop in the trip (optional). e.g. 8
     * @param stopHeadsign The destination displayed on the front of the train (optional).
     *        e.g. City Circle via Museum
     * @param pickupType The type of passenger boarding allowed at this stop (optional).
     * @param dropOffType The type of passenger disembarking allowed at this stop (optional).
     */
    suspend fun insertStopTimes(
        tripId: String,
        arrivalTime: String,
        departureTime: String,
        stopId: String,
        stopSequence: Int?,
        stopHeadsign: String,
        pickupType: Int?,
        dropOffType: Int?,
    )

    /**
     * Adds a batch of stop times to the database.
     *
     * @param stopTimesList A list of StopTimes objects.
     */
    suspend fun insertStopTimesBatch(stopTimesList: List<StopTimes>)

    /**
     * Gets the number of entries in the database for stop times table.
     *
     * @return The number of stop times.
     */
    suspend fun stopTimesSize(): Long
}
