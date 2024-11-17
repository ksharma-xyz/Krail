package xyz.ksharma.krail.trip.planner.ui.timetable.business

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

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
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Suppress("ComplexCondition")
internal fun TripResponse.buildJourneyList(): ImmutableList<TimeTableState.JourneyCardInfo>? =
    journeys?.mapNotNull { journey ->
        val firstPublicTransportLeg = journey.getFirstPublicTransportLeg()
        val lastPublicTransportLeg = journey.getLastPublicTransportLeg()
        val originTimeUTC = firstPublicTransportLeg?.getDepartureTime()
        val arrivalTimeUTC = lastPublicTransportLeg?.getArrivalTime()
        val legs = journey.getFilteredValidLegs()
        val totalStops = legs?.getTotalStops() ?: 0

        legs?.logTransportModes()

        val transportModeLines = legs?.getTransportModeLines()
        val legsList = legs?.getLegsList()

        if (originTimeUTC != null && arrivalTimeUTC != null && totalStops > 0 && legsList != null &&
            transportModeLines != null
        ) {
            // A walking leg consists of walking leg + footpath info from public transport leg
            val walkingDurationStr = legs.sumOf { leg ->
                if (leg.isWalkingLeg()) leg.duration ?: 0L else 0L
            }.let { totalDuration ->
                val footPathDuration = legs.sumOf { leg ->
                    leg.footPathInfo?.firstOrNull()?.duration ?: 0L
                }
                val combinedDuration = totalDuration + footPathDuration
                if (combinedDuration == 0L) {
                    null
                } else {
                    combinedDuration.toDuration(DurationUnit.SECONDS)
                        .toFormattedDurationTimeString()
                }
            }

            TimeTableState.JourneyCardInfo(
                timeText = originTimeUTC.getTimeText(),
                platformText = firstPublicTransportLeg.getPlatformText(),
                platformNumber = firstPublicTransportLeg.getPlatformNumber(),
                originTime = originTimeUTC.fromUTCToDisplayTimeString(),
                originUtcDateTime = originTimeUTC,
                destinationTime = arrivalTimeUTC.fromUTCToDisplayTimeString(),
                travelTime = calculateTimeDifference(
                    originTimeUTC,
                    arrivalTimeUTC,
                ).toFormattedDurationTimeString(),
                totalWalkTime = walkingDurationStr,
                transportModeLines = transportModeLines,
                legs = legsList,
                totalUniqueServiceAlerts = legs.flatMap { leg -> leg.infos.orEmpty() }.toSet().size,
            ).also {
                //Timber.d("\tJourneyId: ${it.journeyId}")
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

private fun TripResponse.Journey.getFilteredValidLegs() = legs?.filter { it.transportation != null }

private fun List<TripResponse.Leg>.getTotalStops() = sumOf { leg -> leg.stopSequence?.size ?: 0 }

fun TripResponse.Leg?.getPlatformText(): String? {
    val disassembledName = this?.origin?.disassembledName ?: return null
    val regex = Regex("(Platform|Stand|Wharf|Side)\\s*(\\d+|[A-Z])", RegexOption.IGNORE_CASE)
    val matches = regex.findAll(disassembledName).toList()
    return if (matches.isNotEmpty()) matches.joinToString(", ") { it.value } else null
}

/**
 * The platform number such as 1, 2 etc. or Stand A, B etc.
 */
fun TripResponse.Leg?.getPlatformNumber(): String? {
    val disassembledName = this?.origin?.disassembledName ?: return null
    val regex = Regex("(Platform|Stand|Wharf)\\s*(\\d+|[A-Z])", RegexOption.IGNORE_CASE)
    val match = regex.find(disassembledName)
    return match?.groupValues?.get(2)
}

private fun List<TripResponse.Leg>.logTransportModes() = forEachIndexed { index, leg ->

    // log origin's disassembledName
    Timber.d("Origin #$index: ${leg.origin?.disassembledName}")
    Timber.d(
        "TransportMode #$index: ${leg.transportation?.product?.productClass}, " +
            "name: ${leg.transportation?.product?.name}, " +
            "stops: ${leg.stopSequence?.size}, " +
            "duration: ${leg.duration}",
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
   // Timber.d("originTime: $it :- ${calculateTimeDifferenceFromNow(utcDateString = it)}")
    calculateTimeDifferenceFromNow(utcDateString = it).toGenericFormattedTimeString()
}

@Suppress("ComplexCondition")
private fun TripResponse.Leg.toUiModel(): TimeTableState.JourneyCardInfo.Leg? {
    Timber.d(
        "\tFFF Leg: ${this.duration}, " +
            "leg: ${this.origin?.name} TO ${this.destination?.name}" +
            " - isWalk:${this.isWalkingLeg()}, " +
            "PClass-${this.transportation?.product?.productClass}",
    )

    val transportMode =
        transportation?.product?.productClass?.toInt()
            ?.let { TransportMode.toTransportModeType(productClass = it) }
    val lineName = transportation?.disassembledName

    val displayText = transportation?.description
    val numberOfStops = stopSequence?.size
    val displayDuration = duration?.seconds?.toFormattedDurationTimeString()
    val stops = stopSequence?.mapNotNull { it.toUiModel() }?.toImmutableList()
    val alerts = infos?.mapNotNull { it.toAlert() }?.toImmutableList()
    alerts?.forEach {
        //Timber.d("\t Alert: ${it.heading.take(5)}, ${it.message.take(5)}, ${it.priority}")
    }
   // Timber.d("Alert---")

    return when {
        // Walking Leg - Always check before public transport leg
        isWalkingLeg() -> {
            displayDuration?.let {
                TimeTableState.JourneyCardInfo.Leg.WalkingLeg(duration = displayDuration)
            }
        }

        else -> { // Public Transport Leg
//            Timber.d("FFF PTLeg: $displayDuration, leg: ${this.destination?.name} ")
            if (transportMode != null && lineName != null && displayText != null &&
                numberOfStops != null && stops != null && displayDuration != null
            ) {
                TimeTableState.JourneyCardInfo.Leg.TransportLeg(
                    transportModeLine = TransportModeLine(
                        transportMode = transportMode,
                        lineName = lineName,
                    ),
                    displayText = displayText,
                    totalDuration = displayDuration,
                    stops = stops,
                    serviceAlertList = alerts,
                    walkInterchange = footPathInfo?.firstOrNull()?.run {
                        duration?.seconds?.toFormattedDurationTimeString()
                            ?.let { formattedDuration -> toWalkInterchange(formattedDuration) }
                    },
                )
            } else {
                null
            }
        }
    }
}

internal fun TripResponse.FootPathInfo.toWalkInterchange(
    displayDuration: String,
): TimeTableState.JourneyCardInfo.WalkInterchange? {
    return position?.let { position ->
        when (position) {
            TimeTableState.JourneyCardInfo.WalkPosition.BEFORE.position -> {
                TimeTableState.JourneyCardInfo.WalkInterchange(
                    duration = displayDuration,
                    position = TimeTableState.JourneyCardInfo.WalkPosition.BEFORE,
                )
            }

            TimeTableState.JourneyCardInfo.WalkPosition.AFTER.position -> {
                TimeTableState.JourneyCardInfo.WalkInterchange(
                    duration = displayDuration,
                    position = TimeTableState.JourneyCardInfo.WalkPosition.AFTER,
                )
            }

            TimeTableState.JourneyCardInfo.WalkPosition.IDEST.position -> {
                TimeTableState.JourneyCardInfo.WalkInterchange(
                    duration = displayDuration,
                    position = TimeTableState.JourneyCardInfo.WalkPosition.IDEST,
                )
            }

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
            isWheelchairAccessible = properties?.wheelchairAccess.toBoolean(),
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
                " LEG#${index + 1} -- Duration: ${leg.duration} -- " +
                    "productClass:${transportationProductClass?.toInt()}",
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

    if (timeArr == null && depTime == null) {
        null
    } else {
        "\n\t\t\t\t Stop: ${it.name}," +
            " depTime: ${timeArr ?: depTime}"
    }
}

private fun String.fromUTCToDisplayTimeString() = this.utcToAEST().aestToHHMM()

private fun TripResponse.Leg.isWalkingLeg(): Boolean =
    transportation?.product?.productClass == 99L || transportation?.product?.productClass == 100L
