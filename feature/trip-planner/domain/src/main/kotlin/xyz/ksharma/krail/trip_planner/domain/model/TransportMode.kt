package xyz.ksharma.krail.trip_planner.domain.model

data class TransportMode(

    /**
     * The product class number as returned by the StopFinder API.
     */
    val productClassNumber: Int,

    /**
     * Map product class number to an internal concrete type for the transport mode.
     */
    val modeType: TransportModeType = when (productClassNumber) {


        /**
         * Sydney Trains: This represents the standard suburban and regional train services operated by Sydney Trains.
         */
        1 -> TransportModeType.SydneyTrain

        /**
         * Intercity Train: This indicates that the stop is served by intercity trains, which connect
         * Sydney to regional areas.
         */
        2 -> TransportModeType.IntercityTrain

        /**
         * Airport Train: This signifies that the stop is on the Airport Line, connecting Sydney's CBD to Sydney Airport.
         */
        3 -> TransportModeType.AirportTrain

        /**
         * Bus: This indicates that bus services operate from this stop.
         */
        4 -> TransportModeType.Bus

        /**
         * Ferry: This signifies that ferry services are available at this location.
         */
        5 -> TransportModeType.Ferry

        /**
         * Coach: This denotes that coach services operate from the stop, typically for longer distance travel.
         */
        6 -> TransportModeType.Coach

        /**
         * On Demand: This indicates that the stop is served by on-demand transport services,
         * such as ride-sharing or community transport.
         */
        7 -> TransportModeType.OnDemand

        /**
         * NightRide: This signifies that the stop is served by NightRide services, which operate
         * late at night on weekends and public holidays.
         */
        8 -> TransportModeType.NightRide

        /**
         * Metro: This denotes that the stop is served by the Sydney Metro network, a fully
         * automated rapid transit system.
         */
        9 -> TransportModeType.Metro

        /**
         * Shuttle: This indicates that the stop is served by shuttle services, typically for
         * specific events or destinations.
         */
        10 -> TransportModeType.Shuttle

        /**
         * Light Rail: This indicates that the stop is served by the Sydney Light Rail network.
         */
        11 -> TransportModeType.LightRail

        else -> TransportModeType.None
    },
) {
    enum class TransportModeType(val modeName: String) {
        Bus("Bus"),
        Ferry(modeName = "Ferry"),
        LightRail(modeName = "Light Rail"),
        Metro(modeName = "Metro"),
        SydneyTrain(modeName = "Sydney Train"),
        IntercityTrain(modeName = "Intercity Train"),
        AirportTrain(modeName = "Airport Train"),
        Coach(modeName = "Coach"),
        OnDemand(modeName = "On Demand"),
        NightRide("Night Ride"),
        Shuttle(modeName = "Shuttle"),
        None("None")
    }
}
