package xyz.ksharma.krail.trip_planner.network.api.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import xyz.ksharma.krail.trip_planner.network.api.model.StopFinderResponse
import xyz.ksharma.krail.trip_planner.network.api.model.TripResponse

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
    suspend fun stopFinder(
        /**
         * Used to set the response data type. This documentation only covers responses that use the JSON format.
         * Setting the outputFormat value to rapidJSON is required to enable JSON output.
         *
         * Available values : rapidJSON
         */
        @Query("outputFormat") outputFormat: String = "rapidJSON",

        /**
         * This specifies the type of results expected in the list of returned stops.
         * By specifying `any`, locations of all types can be returned.
         * If you specifically know that you're searching using a coord, specify `coord`.
         * Likewise, if you're using a stop ID or global stop ID as an input, use `stop`
         * for more accurate results.
         *
         * Available values : any, coord, poi, stop
         */
        @Query("type_sf") typeSf: String,

        /**
         * This is the search term that will be used to find locations.
         * To lookup a coordinate, set type_sf to coord, and use the following format:
         * LONGITUDE:LATITUDE:EPSG:4326 (Note that longitude is first). For example, 151.206290:-33.884080:EPSG:4326.
         * To lookup a stop set type_sf to stop and enter the stop id or global stop ID. For example, 10101100
         *
         * Default value : Circular Quay
         */
        @Query("name_sf") nameSf: String,

        /**
         * This specifies the format the coordinates are returned in.
         * While other variations are available, the EPSG:4326 format will return the widely-used format.
         *
         * Available values : EPSG:4326
         */
        @Query("coordOutputFormat") coordOutputFormat: String = "EPSG:4326",

        /**
         * Including this parameter enables a number of options that result in the stop finder
         * operating in the same way as the Transport for NSW Trip Planner web site.
         *
         * Available values : true
         *
         * Default value : true
         */
        @Query("TfNSWSF") tfNSWSF: String = "true",

        /**
         * Indicates which version of the API the caller is expecting for both request and response
         * data. Note that if this version differs
         * from the version listed above then the returned data may not be as expected.
         *
         * Default value : 10.2.1.42
         */
        @Query("version") version: String? = null,
    ): Response<StopFinderResponse>

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
    suspend fun trip(
        /**
         * Used to set the response data type. This documentation only covers responses that use
         * the JSON format. Setting the outputFormat value to rapidJSON is required to enable JSON output.
         */
        @Query("outputFormat") outputFormat: String = "rapidJSON",

        /**
         * This specifies the format the coordinates are returned in. While other variations are
         * available, the EPSG:4326 format will return the widely-used format.
         */
        @Query("coordOutputFormat") coordOutputFormat: String = "EPSG:4326",

        /**
         * This value anchors the requested date time. If set to dep, then trips departing after
         * the specified date/time at the specified location are included.
         *
         * If set to arr, then trips arriving before the specified time at its destination stop are included.
         * Works in conjunctions with the [itdDate] and [itdTime] values.
         *
         * "dep" or "arr"
         */
        @Query("depArrMacro") depArrMacro: String,

        /**
         * The reference date used when searching trips, in YYYYMMDD format.
         * For instance, 20160901 refers to 1 September 2016. Works in conjunction with the
         * [itdTime] and [depArrMacro] values. If not specified, the current server date is used.
         *
         * Optional, default to null if not provided
         */
        @Query("itdDate") itdDate: String? = null,

        /**
         * The reference time used when searching trips, in HHMM 24-hour format.
         * For instance, 2215 refers to 10:15 PM.
         * Works in conjunction with the [itdDate] and [depArrMacro] values.
         *
         * If not specified, the current server time is used.
         *
         * Optional, default to null if not provided
         */
        @Query("itdTime") itdTime: String? = null,

        /**
         * This is the type of data specified in the name_origin field. The origin indicates the starting point when searching for journeys.
         * The best way to use the trip planner is to use use any for this field then specify a valid location ID in type_origin, or to use coord
         * in this field and a correctly formatted coordinate in type_origin.
         *
         * Available values : any, coord
         *
         * Default value : any
         */
        @Query("type_origin") typeOrigin: String = "any",

        /**
         * This value is used to indicate the starting point when searching for journeys.
         * This value can be one of three things:
         * A valid location/stop ID (for example, 10101100 indicates Central Station - this can be
         * determined using stop_finder).
         *
         * A valid global stop ID (for example, 200060 indicates Central Station - this can be
         * determined using stop_finder) Coordinates in the format LONGITUDE:LATITUDE:EPSG:4326
         * (Note that longitude is first).
         *
         * Default value : 10101331
         */
        @Query("name_origin") nameOrigin: String,

        /**
         * This is the type of data specified in the name_destination field. The origin indicates
         * the finishing point when searching for journeys. The best way to use the trip planner
         * is to use use any for this field then specify a valid location ID in type_destination,
         * or to use coord in this field and a correctly formatted coordinate in type_destination.
         *
         * Available values : any, coord
         *
         * Default value : any
         */
        @Query("type_destination") typeDestination: String = "any",

        /**
         * his value is used to indicate the finishing point when searching for journeys.
         * This value can be one of three things:
         * A valid location/stop ID (for example, 10101100 indicates Central Station - this can be
         * determined using [stopFinder]).
         *
         * A valid global stop ID (for example, 200060 indicates Central Station - this can be
         * determined using [stopFinder])
         * Coordinates in the format LONGITUDE:LATITUDE:EPSG:4326 (Note that longitude is first).
         *
         * Default value : 10102027
         */
        @Query("name_destination") nameDestination: String, // Destination location or coordinates

        /**
         * This parameter indicates the maximum number of trips to returned. Fewer trips may be returned anyway,
         * depending on the available public transport services.
         *
         * Default value : 6
         */
        @Query("calcNumberOfTrips") calcNumberOfTrips: Int = 6,

        /**
         * Including this parameter (regardless of its value) ensures that only wheelchair-accessible
         * options are returned.
         *
         * Available values : on
         */
        @Query("wheelchair") wheelchair: String? = null, // Optional, used for wheelchair accessible trips

        /**
         * This parameter which means of transport to exclude from the trip plan.
         * To exclude one means,
         * select one of the following: 1 = train, 2 = metro, 4 = light rail, 5 = bus, 7 = coach,
         * 9 = ferry, 11 = school bus.
         * `checkbox` allows you to exclude more than one means of transport when used in conjunction
         * with the exclMOT_<ID> parameters.
         *
         * Available values : checkbox, 1, 2, 4, 5, 7, 9, 11
         */
        @Query("excludedMeans") excludedMeans: String? = null, // Optional, to exclude specific transport modes

        /**
         * Excludes train services from the trip plan. Must be used in conjunction with
         * [excludedMeans]=checkbox
         *
         * Available values : 1
         */
        @Query("exclMOT_1") exclMOT1: String? = null, // Optional, to exclude trains

        /**
         * Excludes metro services from the trip plan. Must be used in conjunction with
         * [excludedMeans]=checkbox
         *
         * Available values : 1
         */
        @Query("exclMOT_2") exclMOT2: String? = null, // Optional, to exclude metro

        /**
         * Excludes light rail services from the trip plan. Must be used in conjunction with
         * [excludedMeans]=checkbox
         *
         * Available values : 1
         */
        @Query("exclMOT_4") exclMOT4: String? = null, // Optional, to exclude light rail

        /**
         * Excludes bus services from the trip plan. Must be used in conjunction with
         * [excludedMeans]=checkbox
         *
         * Available values : 1
         */
        @Query("exclMOT_5") exclMOT5: String? = null, // Optional, to exclude bus

        /**
         * Excludes coach services from the trip plan. Must be used in conjunction with
         * [excludedMeans]=checkbox
         *
         * Available values : 1
         */
        @Query("exclMOT_7") exclMOT7: String? = null, // Optional, to exclude bus

        /**
         * Excludes ferry services from the trip plan. Must be used in conjunction with
         * [excludedMeans]=checkbox
         *
         * Available values : 1
         */
        @Query("exclMOT_9") exclMOT9: String? = null, // Optional, to exclude bus

        /**
         * Excludes school bus services from the trip plan. Must be used in conjunction with
         * [excludedMeans]=checkbox
         *
         * Available values : 1
         */
        @Query("exclMOT_11") exclMOT11: String? = null, // Optional, to exclude bus

        /**
         * Including this parameter enables a number of options that result in this API call
         * operating in the same way as the Transport for NSW Trip Planner web site,
         * including enabling real-time data.
         *
         * Available values : true
         *
         * Default value : true
         */
        @Query("TfNSWTR") tfNSWTR: String = "true", // Enables real-time data

        /**
         * Indicates which version of the API the caller is expecting for both request and response
         * data. Note that if this version differs from the version listed above then the returned
         * data may not be as expected.
         *
         * Default value : 10.2.1.42
         */
        @Query("version") version: String = "10.2.1.42", // API version

        /**
         * This parameter activates the options for individual transport. If the parameter is
         * disabled, the parameters concerning individual transport will not be taken into account.
         * possible values are 0 and 1
         *
         * Default value : 1
         */
        @Query("itOptionsActive") itOptionsActive: Int = 1, // Activates individual transport options

        /**
         * Activates the calculation of a monomodal trip, i.e., a trip that takes place exclusively
         * with the means of transport , e.g., with bicycle.
         *
         * Note 1: In order to use this parameter, the options for individual transport must be
         * activated with itOptionsActive=1.
         *
         * Note 2: If no monomodal trip with the means of transport is calculated despite the
         * parameter, the maximum time is often set too low. The parameter MaxITTime applies to
         * all means of transport, the parameter MaxITTimeto the means of transport (e.g., MaxITTime107).
         * These parameters are located in the [Parameters] section or are added to it.
         * The configuration can be alternatively overridden by the [maxTime] parameter.
         *
         */
        @Query("computeMonomodalTripBicycle") computeMonomodalTripBicycle: Boolean = false, // Bike only trip

        @Query("cycleSpeed") cycleSpeed: Int = 16, // Cycling speed in km/h

        @Query("useElevationData") useElevationData: Int = 1, // Takes elevation data into account

        @Query("elevFac") elevFac: Int? = null, // Optional elevation factor
    ): Response<TripResponse>

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
