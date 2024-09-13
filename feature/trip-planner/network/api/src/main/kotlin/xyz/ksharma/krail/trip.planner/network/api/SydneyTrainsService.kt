package xyz.ksharma.krail.trip.planner.network.api

import retrofit2.http.GET

/**
 * https://opendata.transport.nsw.gov.au/dataset/trip-planner-apis/resource/917c66c3-8123-4a0f-b1b1-b4220f32585d
 */
interface TripPlanningService {

    /**
     * This endpoint returns info about stops that match the search criteria. Matches can be
     * sorted on matchQuality to determine the best matches for the given input,  while the best
     * match will be indicated by the isBest value.
     *
     * Provides capability to return all NSW public transport stop, station, wharf,
     * points of interest and known addresses to be used for auto-suggest/auto-complete (to be
     * used with the Trip planner and Departure board APIs).
     */
    @GET("v1/tp/stop_finder")
    suspend fun stopFinder()

    /**
     * This endpoint is used to find a list of journeys between two locations at the specified
     * date and time. For example, if the user is at the Airport and wants to get to Manly using
     * public transport but isn't sure how exactly, this call will tell them exactly which train,
     * bus, ferry or light rail to catch, and between which stops. It is extremely detailed,
     * and includes the the specific path the vehicle(s) will take.
     *
     * Provides capability to provide NSW public transport trip plan options,
     * including walking and driving legs, real-time and Opal fare information.
     */
    @GET("v1/tp/trip")
    suspend fun trip()

    /**
     * This endpoint returns a list of departures for a given location based on the date and time
     * specified. This data can be used to display a "upcoming departures" board for a stop.
     *
     * Provides capability to provide NSW public transport departure information
     * from a stop, station or wharf including real-time.
     */
    @GET("v1/tp/departure_mon")
    suspend fun departure()

    /**
     * This endpoint returns a list of service alerts or additional information about travelling
     * on the public transport network. This list can be filtered by date, route type, route,
     * operator or stop.
     *
     * Provides capability to display all public transport service status and
     * incident information (as published from the Incident Capture System).
     */
    @GET("v1/tp/add_info")
    suspend fun serviceAlert()

    /**
     * When given a specific geographical location, this API finds public transport stops,
     * stations, wharfs and points of interest around that location.
     */
    @GET("v1/tp/coord")
    suspend fun coordinateRequest()
}
