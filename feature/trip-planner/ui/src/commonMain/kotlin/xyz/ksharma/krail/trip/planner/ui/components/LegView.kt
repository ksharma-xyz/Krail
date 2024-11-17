package xyz.ksharma.krail.trip.planner.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import xyz.ksharma.krail.taj.LocalContentAlpha
import xyz.ksharma.krail.taj.components.Text
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.taj.toAdaptiveDecorativeIconSize
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.TransportModeLine
import xyz.ksharma.krail.trip.planner.ui.state.timetable.TimeTableState

@Composable
fun LegView(
    duration: String, // 1h 30m
    routeText: String, // AVC via XYZ
    transportModeLine: TransportModeLine,
    stops: ImmutableList<TimeTableState.JourneyCardInfo.Stop>,
    displayDuration: Boolean,
    modifier: Modifier = Modifier,
    displayAllStops: Boolean = false,
) {
    val circleRadius = 8.dp
    val strokeWidth = 4.dp
    val density = LocalDensity.current
    // todo can be reusable logic for consistent icon size
    val iconSize = with(density) { 14.sp.toDp() }
    val timelineColor =
        remember(transportModeLine) { transportModeLine.lineColorCode.hexToComposeColor() }
    var showIntermediateStops by rememberSaveable { mutableStateOf(displayAllStops) }

    // Content alpha to be 100% always, as it's only visible in the expanded state.
    // If it's visible, it should be full alpha
    CompositionLocalProvider(LocalContentAlpha provides 1f) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .animateContentSize()
                .background(
                    color = transportModeBackgroundColor(transportMode = transportModeLine.transportMode),
                    shape = RoundedCornerShape(12.dp),
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { showIntermediateStops = !showIntermediateStops },
                    role = Role.Button,
                )
                .padding(vertical = 12.dp, horizontal = 12.dp),
        ) {
            RouteSummary(
                routeText = routeText,
                iconSize = iconSize,
                duration = duration,
                displayDuration = displayDuration,
            )

            Spacer(modifier = Modifier.height(12.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                StopInfo(
                    time = stops.first().time,
                    name = stops.first().name,
                    isProminent = true,
                    isWheelchairAccessible = stops.first().isWheelchairAccessible,
                    modifier = Modifier
                        .timeLineTop(
                            color = timelineColor,
                            strokeWidth = strokeWidth,
                            circleRadius = circleRadius,
                        )
                        .padding(start = 16.dp),
                )

                Column(
                    modifier = Modifier
                        .timeLineCenter(
                            color = timelineColor,
                            strokeWidth = strokeWidth,
                        )
                        .padding(start = 16.dp, top = 12.dp),
                ) {
                    if (stops.size > 2) {
                        StopsRow(
                            stops = if (stops.size == 1) "${stops.size} stop" else "${stops.size} stops",
                            line = transportModeLine,
                        )
                    } else {
                        TransportModeInfo(
                            letter = transportModeLine.transportMode.name.first(),
                            backgroundColor = transportModeLine.transportMode.colorCode.hexToComposeColor(),
                            badgeText = transportModeLine.lineName,
                            badgeColor = transportModeLine.lineColorCode.hexToComposeColor(),
                        )
                    }
                }

                if (showIntermediateStops) {
                    stops.drop(1).dropLast(1).forEach { stop ->
                        StopInfo(
                            time = stop.time,
                            name = stop.name,
                            isProminent = false,
                            isWheelchairAccessible = stop.isWheelchairAccessible,
                            modifier = Modifier
                                .timeLineCenterWithStop(
                                    color = timelineColor,
                                    strokeWidth = strokeWidth,
                                    circleRadius = circleRadius,
                                )
                                .timeLineTop(
                                    color = timelineColor,
                                    strokeWidth = strokeWidth,
                                    circleRadius = circleRadius,
                                )
                                .padding(start = 16.dp, top = 12.dp),
                        )
                    }
                }

                StopInfo(
                    time = stops.last().time,
                    name = stops.last().name,
                    isProminent = true,
                    isWheelchairAccessible = stops.last().isWheelchairAccessible,
                    modifier = Modifier
                        .timeLineBottom(
                            color = timelineColor,
                            strokeWidth = strokeWidth,
                            circleRadius = circleRadius,
                        )
                        .padding(start = 16.dp, top = 12.dp),
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun RouteSummary(
    routeText: String,
    iconSize: Dp,
    duration: String,
    displayDuration: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        FlowRow(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = routeText,
                style = KrailTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .align(Alignment.CenterVertically),
            )
        }
        if (displayDuration) {
            Row(horizontalArrangement = Arrangement.End) {
                Image(
                    imageVector = Icons.Filled.Add, //painterResource(R.drawable.ic_clock), // TODO replace with clock icon
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = KrailTheme.colors.onSurface),
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .align(Alignment.CenterVertically)
                        .size(iconSize),
                )
                Text(
                    text = duration,
                    style = KrailTheme.typography.bodySmall,
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StopInfo(
    time: String,
    name: String,
    isProminent: Boolean,
    isWheelchairAccessible: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = time,
            style = if (isProminent) KrailTheme.typography.bodyMedium else KrailTheme.typography.bodySmall,
            color = KrailTheme.colors.onSurface,
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = name,
                style = if (isProminent) KrailTheme.typography.titleSmall else KrailTheme.typography.bodySmall,
                color = KrailTheme.colors.onSurface,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
            if (isWheelchairAccessible) {
                Image(
                    imageVector = Icons.Filled.Add, // TODO - //painterResource(R.drawable.ic_a11y),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        color = if (isProminent) {
                            KrailTheme.colors.onSurface
                        } else {
                            KrailTheme.colors.onSurface.copy(
                                alpha = 0.75f,
                            )
                        },
                    ),
                    modifier = Modifier
                        .size(16.dp.toAdaptiveDecorativeIconSize())
                        .padding(end = 4.dp)
                        .align(Alignment.CenterVertically),
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StopsRow(stops: String, line: TransportModeLine, modifier: Modifier = Modifier) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = line.transportMode.colorCode.hexToComposeColor(),
                    shape = RoundedCornerShape(50),
                )
                .padding(horizontal = 20.dp, vertical = 2.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stops,
                style = KrailTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
            )
        }
        TransportModeInfo(
            letter = line.transportMode.name.first(),
            backgroundColor = line.transportMode.colorCode.hexToComposeColor(),
            badgeText = line.lineName,
            badgeColor = line.lineColorCode.hexToComposeColor(),
            modifier = Modifier.align(Alignment.CenterVertically),
        )
    }
}

// region Previews

@Composable
private fun PreviewLegView() {
    KrailTheme {
        LegView(
            duration = "1h 30m",
            routeText = "towards AVC via XYZ Rd",
            transportModeLine = TransportModeLine(
                transportMode = TransportMode.Bus(),
                lineName = "700",
            ),
            stops = listOf(
                TimeTableState.JourneyCardInfo.Stop(
                    time = "12:00 am",
                    name = "XYZ Station, Platform 1",
                    isWheelchairAccessible = false,
                ),
                TimeTableState.JourneyCardInfo.Stop(
                    time = "12:30 am",
                    name = "ABC Station, Platform 2",
                    isWheelchairAccessible = false,
                ),
                TimeTableState.JourneyCardInfo.Stop(
                    time = "01:00 am",
                    name = "DEF Station, Platform 3",
                    isWheelchairAccessible = true,
                ),
            ).toImmutableList(),
            modifier = Modifier.background(KrailTheme.colors.surface),
            displayDuration = true,
        )
    }
}

@Composable
private fun PreviewLegViewTwoStops() {
    KrailTheme {
        LegView(
            duration = "1h 30m",
            routeText = "towards AVC via XYZ",
            transportModeLine = TransportModeLine(
                transportMode = TransportMode.Train(),
                lineName = "700",
            ),
            stops = listOf(
                TimeTableState.JourneyCardInfo.Stop(
                    time = "12:00 am",
                    name = "XYZ Station, Platform 1",
                    isWheelchairAccessible = true,
                ),
                TimeTableState.JourneyCardInfo.Stop(
                    time = "01:00 am",
                    name = "DEF Station, Platform 3",
                    isWheelchairAccessible = true,
                ),
            ).toImmutableList(),
            modifier = Modifier.background(KrailTheme.colors.surface),
            displayDuration = true,
        )
    }
}

@Composable
private fun PreviewLegViewMetro() {
    KrailTheme {
        LegView(
            duration = "1h 30m",
            routeText = "towards AVC via XYZ",
            transportModeLine = TransportModeLine(
                transportMode = TransportMode.Metro(),
                lineName = "M1",
            ),
            stops = listOf(
                TimeTableState.JourneyCardInfo.Stop(
                    time = "12:00 am",
                    name = "XYZ Station, Platform 1",
                    isWheelchairAccessible = true,
                ),
                TimeTableState.JourneyCardInfo.Stop(
                    time = "01:00 am",
                    name = "DEF Station, Platform 3",
                    isWheelchairAccessible = true,
                ),
            ).toImmutableList(),
            modifier = Modifier.background(KrailTheme.colors.surface),
            displayDuration = true,
        )
    }
}

@Composable
private fun PreviewLegViewFerry() {
    KrailTheme {
        LegView(
            duration = "1h 30m",
            routeText = "towards AVC via XYZ",
            transportModeLine = TransportModeLine(
                transportMode = TransportMode.Ferry(),
                lineName = "F2",
            ),
            stops = listOf(
                TimeTableState.JourneyCardInfo.Stop(
                    time = "12:00 am",
                    name = "XYZ Station, Platform 1",
                    isWheelchairAccessible = true,
                ),
                TimeTableState.JourneyCardInfo.Stop(
                    time = "01:00 am",
                    name = "DEF Station, Platform 3",
                    isWheelchairAccessible = true,
                ),
            ).toImmutableList(),
            modifier = Modifier.background(KrailTheme.colors.surface),
            displayDuration = false,
        )
    }
}

@Composable
private fun PreviewLegViewLightRail() {
    KrailTheme {
        LegView(
            duration = "1h 30m",
            routeText = "towards AVC via XYZ",
            transportModeLine = TransportModeLine(
                transportMode = TransportMode.LightRail(),
                lineName = "L1",
            ),
            stops = listOf(
                TimeTableState.JourneyCardInfo.Stop(
                    time = "12:00 am",
                    name = "XYZ Station, Platform 1",
                    isWheelchairAccessible = true,
                ),
                TimeTableState.JourneyCardInfo.Stop(
                    time = "01:00 am",
                    name = "DEF Station, Platform 3",
                    isWheelchairAccessible = false,
                ),
            ).toImmutableList(),
            modifier = Modifier.background(KrailTheme.colors.surface),
            displayDuration = false,
        )
    }
}

@Composable
private fun PreviewStopsRow() {
    KrailTheme {
        StopsRow(
            stops = "3 stops",
            line = TransportModeLine(
                transportMode = TransportMode.Bus(),
                lineName = "700",
            ),
        )
    }
}

@Composable
private fun PreviewProminentStopInfo() {
    KrailTheme {
        StopInfo(
            time = "12:00",
            name = "XYZ Station, Platform 1",
            isProminent = true,
            isWheelchairAccessible = true,
            modifier = Modifier.background(KrailTheme.colors.surface),
        )
    }
}

@Composable
private fun PreviewRouteSummary() {
    KrailTheme {
        RouteSummary(
            routeText = "towards AVC via XYZ Rd",
            iconSize = 14.dp,
            duration = "1h 30m",
            modifier = Modifier.background(KrailTheme.colors.surface),
            displayDuration = true,
        )
    }
}

// endregion