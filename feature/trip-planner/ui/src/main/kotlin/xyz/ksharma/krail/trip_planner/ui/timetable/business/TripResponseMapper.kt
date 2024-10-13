package xyz.ksharma.krail.trip_planner.ui.timetable.business

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import timber.log.Timber
import xyz.ksharma.krail.core.date_time.DateTimeHelper.aestToHHMM
import xyz.ksharma.krail.core.date_time.DateTimeHelper.calculateTimeDifference
import xyz.ksharma.krail.core.date_time.DateTimeHelper.calculateTimeDifferenceFromNow
import xyz.ksharma.krail.core.date_time.DateTimeHelper.formatTo12HourTime
import xyz.ksharma.krail.core.date_time.DateTimeHelper.toFormattedString
import xyz.ksharma.krail.core.date_time.DateTimeHelper.utcToAEST
import xyz.ksharma.krail.trip_planner.network.api.model.TripResponse
import xyz.ksharma.krail.trip_planner.ui.state.TransportMode
import xyz.ksharma.krail.trip_planner.ui.state.TransportModeLine
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableState

internal fun TripResponse.buildJourneyList() = journeys?.map { journey ->

    // TODO -
    //  1. Sanitise data in domain layer.
    //  2. Pass non null items only to display in ViewModel.

    val firstLeg = journey.legs?.firstOrNull()
    val lastLeg = journey.legs?.lastOrNull()

    val originTime = firstLeg?.origin?.departureTimeEstimated
        ?: firstLeg?.origin?.departureTimePlanned
    val arrivalTime = lastLeg?.destination?.arrivalTimeEstimated
        ?: lastLeg?.destination?.arrivalTimePlanned

    val transportationProductClass =
        firstLeg?.transportation?.product?.productClass

    TimeTableState.JourneyCardInfo(
        timeText = originTime?.let {
            calculateTimeDifferenceFromNow(it).toFormattedString()
        }
            ?: "NULL,",

        platformText = when (firstLeg?.transportation?.product?.productClass) {
            // Walk
            99L, 100L -> null

            // Train or Metro
            1L, 2L -> {
                firstLeg.stopSequence?.firstOrNull()?.disassembledName?.split(",")?.lastOrNull()
            }

            // Others
            else -> {
                firstLeg?.stopSequence?.firstOrNull()?.disassembledName
            }
        },

        originTime = originTime?.utcToAEST()?.aestToHHMM() ?: "NULL",

        destinationTime = arrivalTime?.utcToAEST()?.aestToHHMM()
            ?: "NULL",

        travelTime = calculateTimeDifference(
            originTime!!,
            arrivalTime!!
        ).toMinutes().toString() + " mins",
        transportModeLines = journey.legs?.mapNotNull { leg ->
            leg.transportation?.product?.productClass?.toInt()?.let {
                TransportMode.toTransportModeType(productClass = it)
                    ?.let { it1 ->
                        TransportModeLine(
                            transportMode = it1,
                            lineName = leg.transportation?.disassembledName
                                ?: "NULL"
                        )
                    }
            }
        }?.toImmutableList() ?: persistentListOf(),
    )
}?.toImmutableList()

internal fun TripResponse.logForUnderstandingData() {
    Timber.d("Journeys: ${journeys?.size}")
    journeys?.mapIndexed { jindex, j ->
        Timber.d("JOURNEY #${jindex + 1}")
        j.legs?.forEachIndexed { index, leg ->

            val transportationProductClass =
                leg.transportation?.product?.productClass

            Timber.d(
                " LEG#${index + 1} -- Duration: ${leg.duration} -- " +
                        if (transportationProductClass?.toInt() == 99 ||
                            transportationProductClass?.toInt() == 100
                        ) {
                            "Mode: Walking"
                        } else {
                            "Mode: Public"
                        }
            )
            Timber.d(
                "\t\t ORG: ${
                    leg.origin?.departureTimeEstimated?.utcToAEST()
                        ?.formatTo12HourTime()
                }," +
                        " DEST: ${
                            leg.destination?.arrivalTimeEstimated?.utcToAEST()
                                ?.formatTo12HourTime()
                        }, " +
                        //     "Duration: ${leg.duration}, " +
                        // "transportation: ${leg.transportation?.name}",
                        "interchange: ${leg.interchange?.run { "[desc:$desc, type:$type] " }}" +
                        // "leg properties: ${leg.properties}" +
                        // "leg origin properties: ${leg.origin?.properties}"
                        "\n\t\t\t leg stopSequence: ${leg.stopSequence?.interchangeStopsList()}"
            )
        }
    }
}

/**
 * Prints the stops for legs when interchange required.
 */
private fun List<TripResponse.StopSequence>.interchangeStopsList() = this.mapNotNull {
    // TODO - figure role of ARR vs DEP time
    val timeArr = it.arrivalTimeEstimated?.utcToAEST()
        ?.formatTo12HourTime() ?: it.arrivalTimePlanned?.utcToAEST()?.formatTo12HourTime()

    val depTime = it.departureTimeEstimated?.utcToAEST()
        ?.formatTo12HourTime() ?: it.departureTimePlanned?.utcToAEST()?.formatTo12HourTime()

    if (timeArr == null && depTime == null) null else "\n\t\t\t\t Stop: ${it.name}, depTime: ${timeArr ?: depTime}"
}
