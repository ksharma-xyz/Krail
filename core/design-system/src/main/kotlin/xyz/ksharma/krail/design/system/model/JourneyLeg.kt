package xyz.ksharma.krail.design.system.model

data class JourneyLeg(
    val transportLine: TransportModeLine,

    /**
     * Vehicle Headline
     * Train - City Circle via Parramatta
     * **/
    val headline: String,

    /**
     * List of Stops with Departure Time for the Journey.
     */
    val stops: List<Stop>,

    /**
     * 4 Stops (12 min) etc.
     */
    val summary: String,
)
