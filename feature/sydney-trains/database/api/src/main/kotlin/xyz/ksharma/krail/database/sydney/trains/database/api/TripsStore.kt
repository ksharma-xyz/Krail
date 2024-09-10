package xyz.ksharma.krail.database.sydney.trains.database.api

import xyz.ksharma.krail.sydney.trains.database.Trip

/**
 * Represents a local data source for managing trips.txt data received from the
 * Sydney Trains static GTFS data.
 *
 * It represents table "trips" in the database and will store and query data from SQLite db.
 **/
interface TripsStore {

    /**
     * Adds a new trip to the database.
     *
     * @param routeId The unique identifier for the route associated with the trip. e.g. APS_1a
     * @param serviceId The identifier for the service associated with the trip. e.g. 1904.101.130
     * @param tripId The unique identifier for the trip. e.g. 1--A.1904.101.130.B.8.81035887
     * @param tripHeadsign The destination displayed on the front of the vehicle (optional).
     *        e.g. Empty Train
     * @param tripShortName The short name of the trip (optional). e.g. T8
     * @param directionId The direction of the trip (optional). e.g. 0
     * @param blockId The identifier for the block associated with the trip (optional). e.g.
     * @param shapeId The identifier for the shape of the trip (optional). e.g. RTTA_REV
     * @param wheelchairAccessible Indicates if the trip is wheelchair accessible (optional). e.g. 0
     */
    suspend fun insertTrip(
        routeId: String,
        serviceId: String,
        tripId: String,
        tripHeadsign: String?,
        tripShortName: String?,
        directionId: Int?,
        blockId: String?,
        shapeId: String?,
        wheelchairAccessible: Int?
    )

    /**
     * Removes all trips from the database.
     */
    suspend fun clearTrips()

    /**
     * Adds a batch of trips to the database.
     *
     * @param tripsList A list of [Trip] objects.
     */
    suspend fun insertTripsBatch(tripsList: List<Trip>)

    /**
     * Retrieves all trips from the database.
     *
     * @return A list of Trip objects.
     */
    suspend fun getAllTrips(): List<Trip>

    /**
     * Gets the number of entries in the database for trips table.
     *
     * @return The number of trips.
     */
    suspend fun tripsCount(): Long
}
