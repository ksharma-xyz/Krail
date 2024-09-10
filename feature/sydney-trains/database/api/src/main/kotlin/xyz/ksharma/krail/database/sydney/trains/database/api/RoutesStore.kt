package xyz.ksharma.krail.database.sydney.trains.database.api

import xyz.ksharma.krail.sydney.trains.database.Routes

/**
 * Represents a local data source for managing routes.txt data received from the
 * Sydney Trains static GTFS data.
 *
 * It represents table "routes" in the database and will store and query data from SQLite db.
 **/
interface RoutesStore {

    /**
     * Adds a new route to the database.
     *
     * @param routeId The unique identifier for the route. e.g. APS_1a
     * @param agencyId The identifier for the agency operating the route. e.g. SydneyTrains
     * @param routeShortName The short name for the route. e.g. T8
     * @param routeLongName The long name for the route. e.g. City Circle to Macarthur via Airport
     * @param routeDesc A description of the route. e.g. T8 Airport & South Line
     * @param routeType The type of the route. e.g. 2 for Train
     * @param routeUrl The URL for more information about the route (optional).
     * @param routeColor The color representing the route. e.g. 00954C
     * @param routeTextColor The text color for the route. e.g. FFFFFF
     */
    suspend fun insertRoute(
        routeId: String,
        agencyId: String?,
        routeShortName: String,
        routeLongName: String,
        routeDesc: String?,
        routeType: Int,
        routeUrl: String?,
        routeColor: String?,
        routeTextColor: String?
    )

    /**
     * Removes all routes from the database.
     */
    suspend fun clearRoutes()

    /**
     * Adds a batch of routes to the database.
     *
     * @param routesList A list of Route objects.
     */
    suspend fun insertRoutesBatch(routesList: List<Routes>)

    /**
     * Retrieves all routes from the database.
     *
     * @return A list of Route objects.
     */
    suspend fun getAllRoutes(): List<Routes>

    /**
     * Gets the number of entries in the database for routes table.
     *
     * @return The number of routes.
     */
    suspend fun routesCount(): Long
}
