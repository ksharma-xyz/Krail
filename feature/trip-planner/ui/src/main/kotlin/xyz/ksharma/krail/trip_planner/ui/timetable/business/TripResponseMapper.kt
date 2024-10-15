package xyz.ksharma.krail.trip_planner.ui.timetable.business

import androidx.compose.foundation.layout.size
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import timber.log.Timber
import xyz.ksharma.krail.core.date_time.DateTimeHelper.aestToHHMM
import xyz.ksharma.krail.core.date_time.DateTimeHelper.calculateTimeDifference
import xyz.ksharma.krail.core.date_time.DateTimeHelper.calculateTimeDifferenceFromNow
import xyz.ksharma.krail.core.date_time.DateTimeHelper.formatTo12HourTime
import xyz.ksharma.krail.core.date_time.DateTimeHelper.toFormattedDurationTimeString
import xyz.ksharma.krail.core.date_time.DateTimeHelper.toGenericFormattedTimeString
import xyz.ksharma.krail.core.date_time.DateTimeHelper.utcToAEST
import xyz.ksharma.krail.trip_planner.network.api.model.TripResponse
import xyz.ksharma.krail.trip_planner.ui.state.TransportMode
import xyz.ksharma.krail.trip_planner.ui.state.TransportModeLine
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableState

internal fun TripResponse.buildJourneyList(): ImmutableList<TimeTableState.JourneyCardInfo>? =
    journeys?.mapNotNull { journey ->
        val firstPublicTransportLeg = journey.legs?.firstOrNull { leg ->
            leg.transportation?.product?.productClass != 99L &&
                    leg.transportation?.product?.productClass != 100L
        }
        val lastPublicTransportLeg = journey.legs?.lastOrNull { leg ->
            leg.transportation?.product?.productClass != 99L &&
                    leg.transportation?.product?.productClass != 100L
        }
        val originTimeUTC = firstPublicTransportLeg?.origin?.departureTimeEstimated
            ?: firstPublicTransportLeg?.origin?.departureTimePlanned
        val arrivalTimeUTC = lastPublicTransportLeg?.destination?.arrivalTimeEstimated
            ?: lastPublicTransportLeg?.destination?.arrivalTimePlanned
        val legs = journey.legs?.filter {
            it.transportation != null && it.stopSequence != null
        }
        // Sometimes there are no legs, so we need to handle that case, can happen when, the train is Now.
        val totalStops = legs?.sumOf { leg -> leg.stopSequence?.size ?: 0 } ?: 0

        if (legs != null && originTimeUTC != null && arrivalTimeUTC != null && firstPublicTransportLeg != null && totalStops > 0) {
            TimeTableState.JourneyCardInfo(
                timeText = originTimeUTC.let {
                    Timber.d("originTime: $it :- ${calculateTimeDifferenceFromNow(utcDateString = it)}")
                    calculateTimeDifferenceFromNow(utcDateString = it).toGenericFormattedTimeString()
                },

                platformText = when (firstPublicTransportLeg.transportation?.product?.productClass) {
                    // Train or Metro
                    1L, 2L -> {
                        firstPublicTransportLeg.stopSequence?.firstOrNull()?.disassembledName?.split(
                            ","
                        )
                            ?.lastOrNull()
                    }

                    // Other Modes
                    9L, 5L, 4L, 7L -> {
                        firstPublicTransportLeg.stopSequence?.firstOrNull()?.disassembledName
                    }

                    else -> null
                }?.trim(),

                originTime = originTimeUTC.fromUTCToDisplayTimeString(),
                originUtcDateTime = originTimeUTC,
                destinationTime = arrivalTimeUTC.fromUTCToDisplayTimeString(),
                travelTime = calculateTimeDifference(
                    originTimeUTC,
                    arrivalTimeUTC,
                ).toFormattedDurationTimeString(),
                transportModeLines = legs.mapNotNull { leg ->
                    leg.transportation?.product?.productClass?.toInt()?.let { productClass ->
                        val mode = TransportMode.toTransportModeType(productClass)
                        val lineName = leg.transportation?.disassembledName
                        if (mode != null && lineName != null) {
                            TransportModeLine(transportMode = mode, lineName = lineName)
                        } else null
                    }
                }.toImmutableList(),
                legs = legs.mapNotNull { it.toUiModel() }.toImmutableList(),
            ).also {
                Timber.d("\tJourneyId: ${it.journeyId}")
            }

        } else null
    }?.toImmutableList()

private fun TripResponse.Leg.toUiModel(): TimeTableState.JourneyCardInfo.Leg? {
    val transportMode =
        transportation?.product?.productClass?.toInt()
            ?.let { TransportMode.toTransportModeType(productClass = it) }
    val lineName = transportation?.disassembledName

    val displayText = transportation?.description
    val numberOfStops = stopSequence?.size
    val displayDuration = duration.toDisplayString()
    val stops = stopSequence?.mapNotNull { it.toUiModel() }?.toImmutableList()

    return if (transportMode != null && lineName != null && displayText != null && numberOfStops != null && stops != null) {
        TimeTableState.JourneyCardInfo.Leg(
            transportModeLine = TransportModeLine(
                transportMode = transportMode,
                lineName = lineName,
            ),
            displayText = displayText,
            stopsInfo = "$numberOfStops stops ($displayDuration)",
            stops = stops,
            walkInterchange = null,
        )
    } else {
        null
    }
}

private fun TripResponse.StopSequence.toUiModel(): TimeTableState.JourneyCardInfo.Stop? {
    val stopName = disassembledName ?: name
    // For last leg there is no departure time, so using arrival time
    // For first leg there is no arrival time, so using departure time.
    val time =
        departureTimeEstimated ?: departureTimePlanned ?: arrivalTimeEstimated ?: arrivalTimePlanned
    return if (stopName != null && time != null) {
        TimeTableState.JourneyCardInfo.Stop(
            name = stopName,
            time = time.fromUTCToDisplayTimeString(),
        )
    } else null
}

internal fun TripResponse.logForUnderstandingData() {
    Timber.d("Journeys: ${journeys?.size}")
    journeys?.mapIndexed { jindex, j ->
        Timber.d("JOURNEY #${jindex + 1}")
        j.legs?.forEachIndexed { index, leg ->

            val transportationProductClass =
                leg.transportation?.product?.productClass

            Timber.d(
                " LEG#${index + 1} -- Duration: ${leg.duration} -- productClass:${transportationProductClass?.toInt()}"
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


private fun String.fromUTCToDisplayTimeString() = this.utcToAEST().aestToHHMM()

private fun Long?.toDisplayString(): String {
    return "${this?.div(60)} mins"
}
