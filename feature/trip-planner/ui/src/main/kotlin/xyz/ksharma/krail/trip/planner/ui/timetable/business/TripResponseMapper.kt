package xyz.ksharma.krail.trip.planner.ui.timetable.business

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import timber.log.Timber
import xyz.ksharma.krail.core.datetime.DateTimeHelper.aestToHHMM
import xyz.ksharma.krail.core.datetime.DateTimeHelper.calculateTimeDifference
import xyz.ksharma.krail.core.datetime.DateTimeHelper.calculateTimeDifferenceFromNow
import xyz.ksharma.krail.core.datetime.DateTimeHelper.formatTo12HourTime
import xyz.ksharma.krail.core.datetime.DateTimeHelper.toFormattedDurationTimeString
import xyz.ksharma.krail.core.datetime.DateTimeHelper.toGenericFormattedTimeString
import xyz.ksharma.krail.core.datetime.DateTimeHelper.utcToAEST
import xyz.ksharma.krail.trip.planner.network.api.model.TripResponse
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.TransportModeLine
import xyz.ksharma.krail.trip.planner.ui.state.timetable.TimeTableState

internal fun TripResponse.buildJourneyList(): ImmutableList<TimeTableState.JourneyCardInfo>? =
    journeys?.mapNotNull { journey ->
        val firstPublicTransportLeg = journey.getFirstPublicTransportLeg()
        val lastPublicTransportLeg = journey.getLastPublicTransportLeg()
        val originTimeUTC = firstPublicTransportLeg?.getDepartureTime()
        val arrivalTimeUTC = lastPublicTransportLeg?.getArrivalTime()
        val legs = journey.getFilteredValidLegs()
        val totalStops = legs?.getTotalStops() ?: 0
        val platformText = firstPublicTransportLeg?.getPlatformText()

        legs?.logTransportModes()

        val transportModeLines = legs?.getTransportModeLines()
        val legsList = legs?.getLegsList()

        if (legs != null && originTimeUTC != null && arrivalTimeUTC != null && totalStops > 0 && legsList != null) {
            TimeTableState.JourneyCardInfo(
                timeText = originTimeUTC.getTimeText(),
                platformText = platformText,
                originTime = originTimeUTC.fromUTCToDisplayTimeString(),
                originUtcDateTime = originTimeUTC,
                destinationTime = arrivalTimeUTC.fromUTCToDisplayTimeString(),
                travelTime = calculateTimeDifference(
                    originTimeUTC,
                    arrivalTimeUTC,
                ).toFormattedDurationTimeString(),
                transportModeLines = transportModeLines,
                legs = legsList,
            ).also {
                Timber.d("\tJourneyId: ${it.journeyId}")
            }
        } else {
            null
        }
    }?.toImmutableList()

private fun TripResponse.Journey.getFirstPublicTransportLeg() = legs?.firstOrNull { leg ->
    !leg.isWalkingLeg()
}

private fun TripResponse.Journey.getLastPublicTransportLeg() = legs?.lastOrNull { leg ->
    !leg.isWalkingLeg()
}

private fun TripResponse.Leg?.getDepartureTime() =
    this?.origin?.departureTimeEstimated ?: this?.origin?.departureTimePlanned

private fun TripResponse.Leg?.getArrivalTime() =
    this?.destination?.arrivalTimeEstimated ?: this?.destination?.arrivalTimePlanned

private fun TripResponse.Journey.getFilteredValidLegs() = legs?.filter {
    it.transportation != null && it.stopSequence != null
}

private fun List<TripResponse.Leg>.getTotalStops() = sumOf { leg -> leg.stopSequence?.size ?: 0 }

private fun TripResponse.Leg?.getPlatformText() =
    this?.stopSequence?.firstOrNull()?.properties?.platform?.lastOrNull()
        ?.takeIf { it != '0' && it.isLetterOrDigit() }?.uppercaseChar()

private fun List<TripResponse.Leg>.logTransportModes() = forEachIndexed { index, it ->
    Timber.d(
        "TransportMode #$index: ${it.transportation?.product?.productClass}, " +
            "name: ${it.transportation?.product?.name}, " +
            "stops: ${it.stopSequence?.size}, " +
            "duration: ${it.duration}",
    )
}

private fun List<TripResponse.Leg>.getTransportModeLines() = mapNotNull { leg ->
    leg.transportation?.product?.productClass?.toInt()?.let { productClass ->
        val mode = TransportMode.toTransportModeType(productClass)
        val lineName = leg.transportation?.disassembledName
        if (mode != null && lineName != null) {
            TransportModeLine(transportMode = mode, lineName = lineName)
        } else {
            null
        }
    }
}.toImmutableList()

private fun List<TripResponse.Leg>.getLegsList() = mapNotNull { it.toUiModel() }.toImmutableList()

private fun String.getTimeText() = let {
    Timber.d("originTime: $it :- ${calculateTimeDifferenceFromNow(utcDateString = it)}")
    calculateTimeDifferenceFromNow(utcDateString = it).toGenericFormattedTimeString()
}

private fun TripResponse.Leg.toUiModel(): TimeTableState.JourneyCardInfo.Leg? {
    val transportMode =
        transportation?.product?.productClass?.toInt()
            ?.let { TransportMode.toTransportModeType(productClass = it) }
    val lineName = transportation?.disassembledName

    val displayText = transportation?.description
    val numberOfStops = stopSequence?.size
    val displayDuration = duration.toDisplayString()
    val stops = stopSequence?.mapNotNull { it.toUiModel() }?.toImmutableList()

    return when {
        // Walking Leg - Always check before public transport leg
        isWalkingLeg() -> {
            TimeTableState.JourneyCardInfo.Leg.WalkingLeg(duration = displayDuration)
        }

        else -> { // Public Transport Leg
            if (transportMode != null && lineName != null && displayText != null && numberOfStops != null && stops != null) {
                TimeTableState.JourneyCardInfo.Leg.TransportLeg(
                    transportModeLine = TransportModeLine(
                        transportMode = transportMode,
                        lineName = lineName,
                    ),
                    displayText = displayText,
                    stopsInfo = "$numberOfStops stops ($displayDuration)",
                    stops = stops,
                    walkInterchange = footPathInfo?.firstOrNull()?.let { foot ->
                        foot.toWalkInterchange(foot.duration.toDisplayString())
                    },
                )
            } else {
                null
            }
        }
    }
}

internal fun TripResponse.FootPathInfo.toWalkInterchange(displayDuration: String): TimeTableState.JourneyCardInfo.WalkInterchange? {
    return position?.let { position ->
        when (position) {
            TimeTableState.JourneyCardInfo.WalkPosition.BEFORE.position -> TimeTableState.JourneyCardInfo.WalkInterchange(
                duration = displayDuration,
                position = TimeTableState.JourneyCardInfo.WalkPosition.BEFORE,
            )

            TimeTableState.JourneyCardInfo.WalkPosition.AFTER.position -> TimeTableState.JourneyCardInfo.WalkInterchange(
                duration = displayDuration,
                position = TimeTableState.JourneyCardInfo.WalkPosition.AFTER,
            )

            TimeTableState.JourneyCardInfo.WalkPosition.IDEST.position -> TimeTableState.JourneyCardInfo.WalkInterchange(
                duration = displayDuration,
                position = TimeTableState.JourneyCardInfo.WalkPosition.IDEST,
            )

            else -> null
        }
    }
}

private fun TripResponse.StopSequence.toUiModel(): TimeTableState.JourneyCardInfo.Stop? {
    val stopName = disassembledName ?: name
    // For last leg there is no departure time, so using arrival time
    // For first leg there is no arrival time, so using departure time.
    val time =
        departureTimeEstimated ?: departureTimePlanned ?: arrivalTimeEstimated
            ?: arrivalTimePlanned
    return if (stopName != null && time != null) {
        TimeTableState.JourneyCardInfo.Stop(
            name = stopName,
            time = time.fromUTCToDisplayTimeString(),
        )
    } else {
        null
    }
}

internal fun TripResponse.logForUnderstandingData() {
    Timber.d("Journeys: ${journeys?.size}")
    journeys?.mapIndexed { jindex, j ->
        Timber.d("JOURNEY #${jindex + 1}")
        j.legs?.forEachIndexed { index, leg ->

            val transportationProductClass =
                leg.transportation?.product?.productClass

            Timber.d(
                " LEG#${index + 1} -- Duration: ${leg.duration} -- productClass:${transportationProductClass?.toInt()}",
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
                    "\n\t\t\t leg stopSequence: ${leg.stopSequence?.interchangeStopsList()}",
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

private fun TripResponse.Leg.isWalkingLeg(): Boolean =
    transportation?.product?.productClass == 99L || transportation?.product?.productClass == 100L
