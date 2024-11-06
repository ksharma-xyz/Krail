package xyz.ksharma.krail.trip.planner.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import xyz.ksharma.krail.design.system.components.SeparatorIcon
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.design.system.toAdaptiveDecorativeIconSize
import xyz.ksharma.krail.design.system.toAdaptiveSize
import xyz.ksharma.krail.trip.planner.ui.R
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.TransportModeLine
import xyz.ksharma.krail.trip.planner.ui.state.timetable.TimeTableState

/**
 * A card that displays information about a journey.
 * @param timeToDeparture The time until the journey departs.
 * @param originTime The time the journey starts.
 * @param destinationTime The time the journey ends.
 * @param totalTravelTime The total time the journey takes.
 * @param platformNumber The platform or stand number, the journey departs from.
 * @param isWheelchairAccessible Whether the journey is wheelchair accessible.
 * @param transportModeList The list of transport modes used in the journey.
 * @param onClick The action to perform when the card is clicked.
 * @param modifier The modifier to apply to the card.
 */
@Composable
fun JourneyCard(
    timeToDeparture: String,
    originTime: String,
    destinationTime: String,
    totalTravelTime: String,
    isWheelchairAccessible: Boolean,
    legList: ImmutableList<TimeTableState.JourneyCardInfo.Leg>,
    transportModeList: ImmutableList<TransportMode>,
    onClick: () -> Unit,
    cardState: JourneyCardState,
    totalWalkTime: String?,
    modifier: Modifier = Modifier,
    platformNumber: Char? = null,
) {
    val onSurface: Color = KrailTheme.colors.onSurface
    val borderColors = remember(transportModeList) { transportModeList.toColors(onSurface) }
    val themeColor = transportModeList.firstOrNull()?.colorCode?.hexToComposeColor()
        ?: KrailTheme.colors.onSurface

    val density = LocalDensity.current
    // todo can be reusable logic for consistent icon size
    val iconSize = with(density) { 14.sp.toDp() }

    val horizontalCardPadding by animateDpAsState(
        if (cardState == JourneyCardState.DEFAULT) {
            12.dp
        } else {
            0.dp
        },
        label = "cardPadding",
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(color = KrailTheme.colors.surface)
            .then(
                if (cardState == JourneyCardState.DEFAULT) {
                    Modifier.border(
                        width = 2.dp,
                        shape = RoundedCornerShape(12.dp),
                        brush = Brush.linearGradient(colors = borderColors),
                    )
                } else {
                    Modifier
                },
            )
            .padding(
                vertical = 8.dp,
                horizontal = horizontalCardPadding,
            )
            .animateContentSize(),
    ) {
        when (cardState) {
            JourneyCardState.DEFAULT -> DefaultJourneyCardContent(
                timeToDeparture = timeToDeparture,
                originTime = originTime,
                destinationTime = destinationTime,
                totalTravelTime = totalTravelTime,
                isWheelchairAccessible = isWheelchairAccessible,
                themeColor = themeColor,
                transportModeList = transportModeList,
                platformNumber = platformNumber,
                totalWalkTime = totalWalkTime,
                modifier = Modifier.clickable(
                    role = Role.Button,
                    onClick = onClick,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ),
            )

            else -> JourneyCardContent(
                isExpanded = false,
                timeToDeparture = timeToDeparture,
                themeColor = themeColor,
                platformNumber = platformNumber,
                iconSize = iconSize,
                totalTravelTime = totalTravelTime,
                legList = legList,
                modifier = Modifier.clickable(
                    role = Role.Button,
                    onClick = onClick,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ),
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JourneyCardContent(
    isExpanded: Boolean,
    timeToDeparture: String,
    themeColor: Color,
    platformNumber: Char?,
    iconSize: Dp,
    totalTravelTime: String,
    legList: ImmutableList<TimeTableState.JourneyCardInfo.Leg>,
    modifier: Modifier = Modifier,
) {
    val firstTransportLeg = remember(legList) {
        legList.filterIsInstance<TimeTableState.JourneyCardInfo.Leg.TransportLeg>().firstOrNull()
    }

    Column(modifier = modifier) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = timeToDeparture,
                style = KrailTheme.typography.titleLarge,
                color = themeColor,
            )

            platformNumber?.let { platform ->
                firstTransportLeg?.transportModeLine?.transportMode?.buildPlatformText(platform)
                    ?.let { platformText ->
                        Text(
                            text = platformText,
                            style = KrailTheme.typography.titleLarge,
                            color = themeColor,
                        )
                    }
            }
        }
        FlowRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterVertically),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_alert),
                    contentDescription = "Wheelchair accessible",
                    colorFilter = ColorFilter.tint(Color(0xFFF4B400)),
                    modifier = Modifier
                        .size(iconSize),
                )
                Text(
                    text = "Info",
                    style = KrailTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 4.dp),
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(top = 8.dp),
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_clock),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = KrailTheme.colors.onSurface),
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .align(Alignment.CenterVertically)
                        .size(iconSize),
                )
                Text(
                    text = totalTravelTime,
                    style = KrailTheme.typography.bodyLarge,
                )
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
        )

        legList.forEachIndexed { _, leg ->
            when (leg) {
                is TimeTableState.JourneyCardInfo.Leg.WalkingLeg -> {
                    WalkingLeg(
                        duration = leg.duration,
                        modifier = Modifier
                            .padding(vertical = 2.dp),
                    )
                }

                is TimeTableState.JourneyCardInfo.Leg.TransportLeg -> {
                    if (leg.walkInterchange?.position == TimeTableState.JourneyCardInfo.WalkPosition.BEFORE) {
                        leg.walkInterchange?.duration?.let { duration ->
                            WalkingLeg(
                                duration = duration,
                                modifier = Modifier.padding(vertical = 2.dp),
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                    )

                    if (leg.walkInterchange?.position == TimeTableState.JourneyCardInfo.WalkPosition.IDEST) {
                        leg.walkInterchange?.duration?.let { duration ->
                            WalkingLeg(
                                duration = duration,
                                modifier = Modifier.padding(vertical = 2.dp),
                            )
                        }
                    } else {
                        LegView(
                            duration = leg.totalDuration,
                            routeText = leg.displayText,
                            transportModeLine = leg.transportModeLine,
                            stops = leg.stops,
                            isExpanded = isExpanded,
                            modifier = Modifier,
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                    )

                    if (leg.walkInterchange?.position == TimeTableState.JourneyCardInfo.WalkPosition.AFTER) {
                        leg.walkInterchange?.duration?.let { duration ->
                            WalkingLeg(
                                duration = duration,
                                modifier = Modifier.padding(vertical = 2.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DefaultJourneyCardContent(
    timeToDeparture: String,
    originTime: String,
    destinationTime: String,
    totalTravelTime: String,
    isWheelchairAccessible: Boolean,
    themeColor: Color,
    transportModeList: ImmutableList<TransportMode>,
    platformNumber: Char?,
    totalWalkTime: String?,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = timeToDeparture,
                    style = KrailTheme.typography.titleMedium,
                    color = themeColor,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .align(Alignment.CenterVertically),
                )
                WithDensityCheck {
                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        transportModeList.forEachIndexed { index, mode ->
                            TransportModeIcon(
                                letter = mode.name.first(),
                                backgroundColor = mode.colorCode.hexToComposeColor(),
                            )
                            if (index != transportModeList.lastIndex) {
                                SeparatorIcon(modifier = Modifier.align(Alignment.CenterVertically))
                            }
                        }
                    }
                }
            }

            platformNumber?.let { platform -> // todo - extract
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.CenterVertically)
                        .size(28.dp.toAdaptiveDecorativeIconSize())
                        .border(
                            width = 3.dp,
                            color = themeColor,
                            shape = CircleShape,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = platform.toString(),
                        textAlign = TextAlign.Center,
                        style = KrailTheme.typography.labelLarge,
                    )
                }
            }
        }

        Text(
            text = originTime,
            style = KrailTheme.typography.titleMedium,
            color = KrailTheme.colors.onSurface,
            modifier = Modifier.padding(top = 4.dp),
        )

        FlowRow(
            horizontalArrangement = Arrangement.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        ) {
            Text(
                text = destinationTime,
                style = KrailTheme.typography.titleMedium,
                color = KrailTheme.colors.onSurface,
                modifier = Modifier.padding(end = 10.dp),
            )
            TextWithIcon(
                icon = R.drawable.ic_clock,
                text = totalTravelTime,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 10.dp),
            )
            totalWalkTime?.let {
                TextWithIcon(
                    icon = R.drawable.ic_walk,
                    text = totalWalkTime,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 10.dp),
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (isWheelchairAccessible) {
                Image(
                    painter = painterResource(R.drawable.ic_a11y),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = KrailTheme.colors.onSurface),
                    modifier = Modifier
                        .size(14.dp.toAdaptiveSize())
                        .align(Alignment.CenterVertically),
                )
            }
        }
    }
}

@Composable
private fun TextWithIcon(icon: Int, text: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = KrailTheme.colors.onSurface),
            modifier = Modifier
                .padding(end = 4.dp)
                .align(Alignment.CenterVertically)
                .size(14.dp.toAdaptiveSize()),
        )
        Text(
            text = text,
            style = KrailTheme.typography.bodyMedium,
        )
    }
}

// toColors() now accepts onSurface color as a parameter
internal fun List<TransportMode>?.toColors(onSurface: Color): List<Color> = when {
    this.isNullOrEmpty() -> listOf(onSurface, onSurface)
    size >= 2 -> map { it.colorCode.hexToComposeColor() }
    else -> {
        val color = first().colorCode.hexToComposeColor()
        listOf(color, color)
    }
}

internal fun TransportMode.buildPlatformText(platformNumber: Char): String? {
    return when (this) {
        is TransportMode.Train, is TransportMode.Metro -> "Platform $platformNumber"
        is TransportMode.Bus -> "Stand $platformNumber"
        is TransportMode.Ferry -> "Wharf $platformNumber"
        else -> null
    }
}

// region Previews

@PreviewLightDark
@Preview(fontScale = 2f)
@Composable
private fun PreviewJourneyCard() {
    KrailTheme {
        JourneyCard(
            timeToDeparture = "in 5 mins",
            originTime = "8:25am",
            destinationTime = "8:40am",
            totalTravelTime = "15 mins",
            platformNumber = '1',
            isWheelchairAccessible = true,
            transportModeList = listOf(
                TransportMode.Train(),
                TransportMode.Bus(),
            ).toImmutableList(),
            legList = persistentListOf(),
            cardState = JourneyCardState.DEFAULT,
            totalWalkTime = null,
            onClick = {},
        )
    }
}

@PreviewLightDark
@Preview(fontScale = 2f)
@Composable
private fun PreviewJourneyCardCollapsed() {
    KrailTheme {
        JourneyCard(
            timeToDeparture = "in 5 mins",
            originTime = "8:25am",
            destinationTime = "8:40am",
            totalTravelTime = "15 mins",
            platformNumber = '1',
            isWheelchairAccessible = true,
            transportModeList = listOf(
                TransportMode.Train(),
                TransportMode.Bus(),
            ).toImmutableList(),
            legList = persistentListOf(
                TimeTableState.JourneyCardInfo.Leg.TransportLeg(
                    stops = PREVIEW_STOPS,
                    displayText = "towards Abc via Rainy Rd",
                    transportModeLine = TransportModeLine(
                        transportMode = TransportMode.Train(),
                        lineName = "T1",
                    ),
                    totalDuration = "20 mins",
                ),
                TimeTableState.JourneyCardInfo.Leg.WalkingLeg(
                    duration = "15 mins",
                ),
                TimeTableState.JourneyCardInfo.Leg.TransportLeg(
                    stops = PREVIEW_STOPS.take(2).toImmutableList(),
                    displayText = "towards Xyz via Awesome Rd",
                    totalDuration = "10 mins",
                    transportModeLine = TransportModeLine(
                        transportMode = TransportMode.Bus(),
                        lineName = "700",
                    ),
                ),

            ),
            cardState = JourneyCardState.COLLAPSED,
            totalWalkTime = "10 mins",
            onClick = {},
        )
    }
}

@PreviewLightDark
@Preview(fontScale = 2f)
@Composable
private fun PreviewJourneyCardExpanded() {
    KrailTheme {
        JourneyCard(
            timeToDeparture = "in 5 mins",
            originTime = "8:25am",
            destinationTime = "8:40am",
            totalTravelTime = "15 mins",
            platformNumber = '1',
            isWheelchairAccessible = true,
            transportModeList = listOf(
                TransportMode.Train(),
                TransportMode.Bus(),
            ).toImmutableList(),
            legList = persistentListOf(
                TimeTableState.JourneyCardInfo.Leg.TransportLeg(
                    stops = PREVIEW_STOPS,
                    displayText = "towards Abc via Rainy Rd",
                    transportModeLine = TransportModeLine(
                        transportMode = TransportMode.Train(),
                        lineName = "T1",
                    ),
                    totalDuration = "20 mins",
                ),
                TimeTableState.JourneyCardInfo.Leg.WalkingLeg(
                    duration = "15 mins",
                ),
                TimeTableState.JourneyCardInfo.Leg.TransportLeg(
                    stops = PREVIEW_STOPS.take(2).toImmutableList(),
                    displayText = "towards Xyz via Awesome Rd",
                    totalDuration = "10 mins",
                    transportModeLine = TransportModeLine(
                        transportMode = TransportMode.Bus(),
                        lineName = "700",
                    ),
                ),

            ),
            cardState = JourneyCardState.EXPANDED,
            totalWalkTime = null,
            onClick = {},
        )
    }
}

private val PREVIEW_STOPS = persistentListOf(
    TimeTableState.JourneyCardInfo.Stop(
        name = "Stop 1",
        time = "8:30am",
    ),
    TimeTableState.JourneyCardInfo.Stop(
        name = "Stop 2",
        time = "8:35am",
    ),
    TimeTableState.JourneyCardInfo.Stop(
        name = "Stop 3",
        time = "8:40am",
    ),
)

@Preview
@Preview(fontScale = 2f)
@Composable
private fun PreviewJourneyCardLongData() {
    KrailTheme {
        JourneyCard(
            timeToDeparture = "in 1h 5mins",
            originTime = "12:25am",
            destinationTime = "12:40am",
            totalTravelTime = "45h 15minutes",
            platformNumber = 'A',
            isWheelchairAccessible = true,
            transportModeList = listOf(
                TransportMode.Ferry(),
                TransportMode.Bus(),
                TransportMode.Train(),
                TransportMode.Coach(),
                TransportMode.Metro(),
            ).toImmutableList(),
            legList = persistentListOf(),
            cardState = JourneyCardState.DEFAULT,
            totalWalkTime = "15 mins",
            onClick = {},
        )
    }
}

// endregion
