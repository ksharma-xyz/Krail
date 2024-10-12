package xyz.ksharma.krail.trip_planner.ui.state.timetable

import kotlinx.collections.immutable.ImmutableList
import xyz.ksharma.krail.trip_planner.ui.state.TransportModeLine

data class JourneyDetail(
    val timeText: String, // "in x mins" - journeys.legs.first().origin.departureTimePlanned with Time.now()
    val platformText: String, // "on Platform X" - journeys.legs.first().stopSequence.disassembledName
    val journeyTime: String, // "(17 mins)" - journeys.legs.sumBy { it.duration }
    val legs: ImmutableList<Leg>,
) {

    data class Leg(
        // modeType - legs.transportation.product.class
        // lineName - legs.transportation.disassembledName
        val transportModeLine: TransportModeLine,

        // transportation.description -> "Burwood to Liverpool",
        val displayText: String, // "towards X via X"

        // leg.stopSequence.size  (leg.duration seconds)
        val stopsInfo: String, // "4 stops (12 min)"

        val stops: ImmutableList<Stop>,

        val walkInterchange: WalkInterchange,
    )

    data class WalkInterchange(
        // leg.footPathInfo.duration seconds
        val duration: String,

        // leg.footPathInfo.position
        val position: WalkPosition,
    )

    enum class WalkPosition {
        /**
         * Need to walk before the Leg starts.
         */
        BEFORE,

        /**
         * After displaying the Leg info, we need to walk.
         */
        AFTER,

        /**
         * This indicates that the walking portion of the leg is the entire leg itself.
         * In other words, the leg involves walking only, with no vehicle transportation involved.
         * For example, if you're planning a trip from one location to another that involves walking
         * the entire distance, the "position" would be "IDEST".
         */
        IDEST,
    }

    // TODO - Add hint support. leg.hints
// TODO Add interchange facility between stops. - it represents walking from one stop to another.
    data class Stop(
        val name: String, // "xx Station, Platform 1" - stopSequence.disassembledName ?: stopSequence.name
        val time: String, // "12:00pm" - stopSequence.departureTimeEstimated ?: stopSequence.departureTimePlanned
    )
}
