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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import xyz.ksharma.krail.taj.LocalContentAlpha
import xyz.ksharma.krail.taj.components.SeparatorIcon
import xyz.ksharma.krail.taj.components.Text
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.taj.theme.getForegroundColor
import xyz.ksharma.krail.taj.toAdaptiveDecorativeIconSize
import xyz.ksharma.krail.taj.toAdaptiveSize
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
    platformNumber: String?,
    platformText: String?,
    originTime: String,
    destinationTime: String,
    totalTravelTime: String,
    isWheelchairAccessible: Boolean,
    legList: ImmutableList<TimeTableState.JourneyCardInfo.Leg>,
    transportModeList: ImmutableList<TransportMode>,
    onClick: () -> Unit,
    cardState: JourneyCardState,
    totalWalkTime: String?,
    totalUniqueServiceAlerts: Int,
    modifier: Modifier = Modifier,
    onAlertClick: () -> Unit = {},
) {
    val onSurface: Color = KrailTheme.colors.onSurface
    val borderColors = remember(transportModeList) { transportModeList.toColors(onSurface) }
    val isPastJourney by remember(timeToDeparture) {
        mutableStateOf(
            timeToDeparture.contains(other = "ago", ignoreCase = true),
        )
    }
    val pastJourneyColor = KrailTheme.colors.onSurface.copy(alpha = 0.5f)
    val themeColor = if (!isPastJourney) {
        transportModeList.firstOrNull()?.colorCode?.hexToComposeColor()
            ?: KrailTheme.colors.onSurface
    } else {
        pastJourneyColor
    }

    val density = LocalDensity.current
    // todo can be reusable logic for consistent icon size
    val iconSize = with(density) { 14.sp.toDp() }

    val horizontalCardPadding by animateDpAsState(
        targetValue = if (cardState == JourneyCardState.DEFAULT) 12.dp else 0.dp,
        label = "cardPadding",
    )

    CompositionLocalProvider(LocalContentAlpha provides if (isPastJourney) 0.5f else 1f) {
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
                            brush = if (!isPastJourney) {
                                Brush.linearGradient(colors = borderColors)
                            } else {
                                Brush.linearGradient(listOf(pastJourneyColor, pastJourneyColor))
                            },
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
                    modifier = Modifier
                        .semantics(mergeDescendants = true) { }
                        .clickable(
                            role = Role.Button,
                            onClick = onClick,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ),
                )

                JourneyCardState.EXPANDED -> ExpandedJourneyCardContent(
                    displayAllStops = false,
                    timeToDeparture = timeToDeparture,
                    themeColor = themeColor,
                    platformText = platformText,
                    iconSize = iconSize,
                    totalTravelTime = totalTravelTime,
                    legList = legList,
                    totalUniqueServiceAlerts = totalUniqueServiceAlerts,
                    onAlertClick = onAlertClick,
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
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExpandedJourneyCardContent(
    displayAllStops: Boolean,
    timeToDeparture: String,
    themeColor: Color,
    platformText: String?,
    iconSize: Dp,
    totalTravelTime: String,
    legList: ImmutableList<TimeTableState.JourneyCardInfo.Leg>,
    totalUniqueServiceAlerts: Int,
    onAlertClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = if (platformText != null) Arrangement.SpaceBetween else Arrangement.Start,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = timeToDeparture,
                style = KrailTheme.typography.titleLarge,
                color = themeColor,
            )

            platformText?.let { text ->
                Text(
                    text = text,
                    style = KrailTheme.typography.titleLarge,
                    color = themeColor,
                )
            }
        }
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = if (totalUniqueServiceAlerts > 0) {
                Arrangement.SpaceBetween
            } else {
                Arrangement.End
            },
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            if (totalUniqueServiceAlerts > 0) {
                SmallButton(
                    imageVector = Icons.Filled.Call, // TODO - R.drawable.ic_alert,
                    text = if(totalUniqueServiceAlerts > 1) {
                        "$totalUniqueServiceAlerts Alerts"
                    } else {
                        "$totalUniqueServiceAlerts Alert"
                    },
                    color = getForegroundColor(KrailTheme.colors.alert),
                    iconSize = iconSize,
                    onClick = onAlertClick,
                    modifier = Modifier.align(Alignment.CenterVertically),
                )
            }

            TextWithIcon(
                imageVector = Icons.Filled.ShoppingCart,//TODO - R.drawable.ic_clock,
                text = totalTravelTime,
                textStyle = KrailTheme.typography.bodyLarge,
                iconSize = iconSize,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
        )

        legList.forEachIndexed { _, leg ->
            when (leg) {
                is TimeTableState.JourneyCardInfo.Leg.WalkingLeg -> {
                    WalkingLeg(
                        duration = leg.duration,
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 2.dp),
                    )
                }

                is TimeTableState.JourneyCardInfo.Leg.TransportLeg -> {
                    if (leg.walkInterchange?.position == TimeTableState.JourneyCardInfo.WalkPosition.BEFORE) {
                        leg.walkInterchange?.duration?.let { duration ->
                            WalkingLeg(
                                duration = duration,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp),
                            )
                        }
                    }

                    if (leg.walkInterchange?.position == TimeTableState.JourneyCardInfo.WalkPosition.IDEST) {
                        leg.walkInterchange?.duration?.let { duration ->
                            WalkingLeg(
                                duration = duration,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp),
                            )
                        }
                    } else {
                        LegView(
                            displayDuration = legList.count {
                                it is TimeTableState.JourneyCardInfo.Leg.TransportLeg
                            } > 1,
                            duration = leg.totalDuration,
                            routeText = leg.displayText,
                            transportModeLine = leg.transportModeLine,
                            stops = leg.stops,
                            displayAllStops = displayAllStops,
                            modifier = Modifier,
                        )
                    }

                    if (leg.walkInterchange?.position == TimeTableState.JourneyCardInfo.WalkPosition.AFTER) {
                        leg.walkInterchange?.duration?.let { duration ->
                            WalkingLeg(
                                duration = duration,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp),
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
    platformNumber: String?,
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
                DisplayWithDensityCheck {
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
                imageVector = Icons.Filled.ShoppingCart, //TODO - R.drawable.ic_clock,
                text = totalTravelTime,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 10.dp),
            )
            totalWalkTime?.let {
                TextWithIcon(
                    imageVector = Icons.Filled.ShoppingCart,// TODO - R.drawable.ic_walk,
                    text = totalWalkTime,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 10.dp),
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (isWheelchairAccessible) {
                Image(
                    imageVector = Icons.Outlined.Add,
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
private fun TextWithIcon(
    imageVector: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = KrailTheme.typography.bodyMedium,
    color: Color = KrailTheme.colors.onSurface,
    iconSize: Dp = 14.dp,
) {
    val contentAlpha = LocalContentAlpha.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .semantics(mergeDescendants = true) { },
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Image(
            imageVector = imageVector,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = color.copy(alpha = contentAlpha)),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(iconSize.toAdaptiveSize()),
        )
        Text(
            text = text,
            style = textStyle,
            color = color,
        )
    }
}

@Composable
private fun SmallButton(
    imageVector: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = KrailTheme.typography.bodyMedium,
    color: Color = KrailTheme.colors.onSurface,
    iconSize: Dp = 14.dp,
    onClick: () -> Unit = {},
) {
    val contentAlpha = LocalContentAlpha.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                shape = RoundedCornerShape(50),
                color = KrailTheme.colors.alert.copy(alpha = contentAlpha),
            )
            .padding(
                horizontal = 8.dp,
                vertical = 2.dp,
            )
            .clickable(
                role = Role.Button,
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onClick() },
            )
            .semantics(mergeDescendants = true) { },
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Image(
            imageVector = imageVector,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = color.copy(alpha = contentAlpha)),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(iconSize.toAdaptiveSize()),
        )
        Text(
            text = text,
            style = textStyle,
            color = color,
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

// region Previews


//@Preview(fontScale = 2f)
@Composable
private fun PreviewJourneyCard() {
    KrailTheme {
        JourneyCard(
            timeToDeparture = "in 5 mins",
            originTime = "8:25am",
            destinationTime = "8:40am",
            totalTravelTime = "15 mins",
            platformNumber = "18",
            platformText = "Platform 18",
            isWheelchairAccessible = true,
            transportModeList = listOf(
                TransportMode.Train(),
                TransportMode.Bus(),
            ).toImmutableList(),
            legList = persistentListOf(),
            cardState = JourneyCardState.DEFAULT,
            totalWalkTime = null,
            totalUniqueServiceAlerts = 0,
            onClick = {},
        )
    }
}


//@Preview(fontScale = 2f)
@Composable
private fun PreviewJourneyCardCollapsed() {
    KrailTheme {
        JourneyCard(
            timeToDeparture = "in 5 mins",
            originTime = "8:25am",
            destinationTime = "8:40am",
            totalTravelTime = "15 mins",
            platformNumber = "1",
            platformText = "Platform 1",
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
            totalWalkTime = "10 mins",
            totalUniqueServiceAlerts = 1,
            onClick = {},
        )
    }
}

@Composable
private fun PreviewJourneyCardExpanded() {
    KrailTheme {
        JourneyCard(
            timeToDeparture = "in 5 mins",
            originTime = "8:25am",
            destinationTime = "8:40am",
            totalTravelTime = "15 mins",
            platformNumber = "3",
            platformText = "Platform 3",
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
            totalUniqueServiceAlerts = 0,
            onClick = {},
        )
    }
}

private val PREVIEW_STOPS = persistentListOf(
    TimeTableState.JourneyCardInfo.Stop(
        name = "Stop 1",
        time = "8:30am",
        isWheelchairAccessible = true,
    ),
    TimeTableState.JourneyCardInfo.Stop(
        name = "Stop 2",
        time = "8:35am",
        isWheelchairAccessible = true,
    ),
    TimeTableState.JourneyCardInfo.Stop(
        name = "Stop 3",
        time = "8:40am",
        isWheelchairAccessible = false,
    ),
)

@Composable
private fun PreviewJourneyCardLongData() {
    KrailTheme {
        JourneyCard(
            timeToDeparture = "in 1h 5mins",
            originTime = "12:25am",
            destinationTime = "12:40am",
            totalTravelTime = "45h 15minutes",
            platformNumber = "A",
            platformText = "Stand A",
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
            totalUniqueServiceAlerts = 2,
            onClick = {},
        )
    }
}

// endregion