package xyz.ksharma.krail.trip.planner.ui.state.timetable

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import xyz.ksharma.krail.trip.planner.ui.state.TransportModeLine
import xyz.ksharma.krail.trip.planner.ui.state.alerts.Alert

data class TimeTableState(
    val isLoading: Boolean = false,
    val isTripSaved: Boolean = false,
    val journeyList: ImmutableList<JourneyCardInfo> = persistentListOf(),
    val trip: Trip? = null,
) {
    data class JourneyCardInfo(
        val timeText: String, // "in x mins"

        val platformText: Char? = null, // "on Platform X" or Stand A etc.

        // If first leg is not walking then,
        //      leg.first.origin.departureTimeEstimated ?: leg.first.origin.departureTimePlanned
        // else leg.first.destination.arrivalTimeEstimated ?: leg.first.destination.arrivalTimePlanned
        val originTime: String, // "11:30pm" stopSequence.arrivalTimeEstimated ?: stopSequence.arrivalTimePlanned

        val originUtcDateTime: String, // "2024-09-24T19:00:00Z"

        // legs.last.destination.arrivalTimeEstimated ?: legs.last.destination.arrivalTimePlanned
        val destinationTime: String, // "11:40pm"

        // legs.sumBy { it.duration } - seconds
        val travelTime: String, // "(10 min)"

        /**
         * Total walking time in the journey.
         */
        val totalWalkTime: String? = null, // "10 mins"

        /**
         * transportation.disassembledName -> lineName
         * transportation.product.class -> TransportModeType
         */
        val transportModeLines: ImmutableList<TransportModeLine>,

        val legs: ImmutableList<Leg>,
    ) {
        val journeyId: String
            get() = buildString {
                append(originUtcDateTime)
                append(destinationTime)
                append(
                    transportModeLines.joinToString { it.lineName }
                        .filter { it.isLetterOrDigit() },
                )
            }

        sealed class Leg {
            data class WalkingLeg(
                val duration: String, // "10mins"
            ) : Leg()

            data class TransportLeg(
                // modeType - legs.transportation.product.class
                // lineName - legs.transportation.disassembledName
                val transportModeLine: TransportModeLine,

                // transportation.description -> "Burwood to Liverpool",
                val displayText: String, // "towards X via X"

                // leg.stopSequence.size  (leg.duration seconds)
                val totalDuration: String, // 12 min"

                val stops: ImmutableList<Stop>,

                val walkInterchange: WalkInterchange? = null,

                // Service Alerts for the leg.
                val alertList: ImmutableList<Alert>? = null,
            ) : Leg()
        }

        data class WalkInterchange(
            // leg.footPathInfo.duration seconds
            val duration: String,

            // leg.footPathInfo.position
            val position: WalkPosition,
        )

        enum class WalkPosition(val position: String) {
            /**
             * Need to walk before the Leg starts.
             */
            BEFORE("BEFORE"),

            /**
             * After displaying the Leg info, we need to walk.
             */
            AFTER("AFTER"),

            /**
             * This indicates that the walking portion of the leg is the entire leg itself.
             * In other words, the leg involves walking only, with no vehicle transportation involved.
             * For example, if you're planning a trip from one location to another that involves walking
             * the entire distance, the "position" would be "IDEST".
             */
            IDEST("IDEST"),
        }

        data class Stop(
            val name: String, // "xx Station, Platform 1" - stopSequence.disassembledName ?: stopSequence.name
            val time: String, // "12:00pm" - stopSequence.departureTimeEstimated ?: stopSequence.departureTimePlanned
        )
    }
}
