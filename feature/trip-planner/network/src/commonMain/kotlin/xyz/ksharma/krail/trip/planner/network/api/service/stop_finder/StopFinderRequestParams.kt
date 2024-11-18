package xyz.ksharma.krail.trip.planner.network.api.service.stop_finder

internal object StopFinderRequestParams {

    /**
     * Used to set the response data type. This documentation only covers responses that use the JSON format.
     * Setting the outputFormat value to rapidJSON is required to enable JSON output.
     *
     * Available values : rapidJSON
     */
    const val outputFormat: String = "outputFormat"

    /**
     * This specifies the type of results expected in the list of returned stops.
     * By specifying `any`, locations of all types can be returned.
     * If you specifically know that you're searching using a coord, specify `coord`.
     * Likewise, if you're using a stop ID or global stop ID as an input, use `stop`
     * for more accurate results.
     *
     * Available values : any, coord, poi, stop
     */
    const val typeSf: String = "type_sf"

    /**
     * This is the search term that will be used to find locations.
     * To lookup a coordinate, set type_sf to coord, and use the following format:
     * LONGITUDE:LATITUDE:EPSG:4326 (Note that longitude is first). For example, 151.206290:-33.884080:EPSG:4326.
     * To lookup a stop set type_sf to stop and enter the stop id or global stop ID. For example, 10101100
     *
     * Default value : Circular Quay
     */
    const val nameSf: String = "name_sf"

    /**
     * This specifies the format the coordinates are returned in.
     * While other variations are available, the EPSG:4326 format will return the widely-used format.
     *
     * Available values : EPSG:4326
     */
    const val coordOutputFormat = "coordOutputFormat" // "EPSG:4326",

    /**
     * Including this parameter enables a number of options that result in the stop finder
     * operating in the same way as the Transport for NSW Trip Planner web site.
     *
     * Available values : true
     *
     * Default value : true
     */
    const val tfNSWSF = "TfNSWSF"

    /**
     * Indicates which version of the API the caller is expecting for both request and response
     * data. Note that if this version differs
     * from the version listed above then the returned data may not be as expected.
     *
     * Default value : 10.2.1.42
     */
    const val version = "version"
}
