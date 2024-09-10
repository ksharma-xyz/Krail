package xyz.ksharma.krail.database.sydney.trains.database.api

import xyz.ksharma.krail.sydney.trains.database.Stop

/**
 * Represents a local data source for managing stops.txt data received from the
 * Sydney Trains static GTFS data.
 *
 * It represents table "stop" in the database and will store and query data from SQLite db.
 **/
interface StopsStore {

    /**
     * Adds a new stop to the database.
     *
     * @param stopId The unique identifier for the stop. e.g. 2142324
     * @param stopCode The code for the stop (optional). e.g. 12345
     * @param stopName The name of the stop. e.g. Central Station
     * @param stopDesc A description of the stop (optional). e.g. Main city station
     * @param stopLat The latitude of the stop location. e.g. -33.8688
     * @param stopLon The longitude of the stop location. e.g. 151.2093
     * @param zoneId The zone in which the stop is located (optional). e.g. 1
     * @param stopUrl The URL for more information about the stop (optional). e.g. http://example.com
     * @param locationType The type of location for the stop. e.g. 1 (Station)
     * @param parentStation The ID of the parent station if this stop is a substation (optional). e.g. 1234
     * @param stopTimezone The timezone of the stop (optional). e.g. Australia/Sydney
     * @param wheelchairBoarding Indicates if wheelchair boarding is accessible at this stop (optional). e.g. 0
     */
    suspend fun insertStop(
        stopId: String,
        stopCode: String?,
        stopName: String,
        stopDesc: String?,
        stopLat: Double,
        stopLon: Double,
        zoneId: String?,
        stopUrl: String?,
        locationType: Int?,
        parentStation: String?,
        stopTimezone: String?,
        wheelchairBoarding: Int?
    )

    /**
     * Removes all stops from the database.
     */
    suspend fun clearStops()

    /**
     * Adds a batch of stops to the database.
     *
     * @param stopsList A list of [Stop] objects.
     */
    suspend fun insertStopsBatch(stopsList: List<Stop>)

    /**
     * Retrieves all stops from the database.
     *
     * @return A list of [Stop] objects.
     */
    suspend fun getAllStops(): List<Stop>

    /**
     * Gets the number of entries in the database for stops table.
     *
     * @return The number of stops.
     */
    suspend fun stopsCount(): Long
}
