package xyz.ksharma.krail.trip.planner.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip.planner.ui.R
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.TransportModeLine
import xyz.ksharma.krail.trip.planner.ui.state.timetable.TimeTableState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LegView(
    duration: String, // 1h 30m
    routeText: String, // AVC via XYZ
    transportModeLine: TransportModeLine,
    stops: ImmutableList<TimeTableState.JourneyCardInfo.Stop>,
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
) {
    val circleRadius = 8.dp
    val strokeWidth = 4.dp
    val density = LocalDensity.current
    // todo can be reusable logic for consistent icon size
    val iconSize = with(density) { 14.sp.toDp() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = transportModeLine.transportMode.colorCode
                    .hexToComposeColor()
                    .copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp),
            )
            .padding(vertical = 12.dp, horizontal = 12.dp),
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = routeText,
                style = KrailTheme.typography.bodySmall,

                modifier = Modifier
                    .padding(end = 12.dp)
                    .align(Alignment.CenterVertically),
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.CenterVertically),
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_clock),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = KrailTheme.colors.onBackground),
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .align(Alignment.CenterVertically)
                        .size(iconSize),
                )
                Text(
                    text = duration,
                    style = KrailTheme.typography.bodySmall,
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            StopInfo(
                time = stops.first().time,
                name = stops.first().name,
                isProminent = true,
                modifier = Modifier
                    .timeLineTop(
                        color = transportModeLine.transportMode.colorCode.hexToComposeColor(),
                        strokeWidth = strokeWidth,
                        circleRadius = circleRadius,
                    )
                    .padding(start = 16.dp),
            )

            Column(
                modifier = Modifier
                    .timeLineCenter(
                        color = transportModeLine.transportMode.colorCode.hexToComposeColor(),
                        strokeWidth = strokeWidth,
                    )
                    .padding(start = 16.dp, top = 12.dp),
            ) {
                if (stops.size > 2) {
                    StopsRow(
                        stops = "${stops.size - 2} stops",
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

            if (isExpanded) {
                stops.drop(1).dropLast(1).forEach { stop ->
                    StopInfo(
                        time = stop.time,
                        name = stop.name,
                        isProminent = false,
                        modifier = Modifier
                            .timeLineCenterWithStop(
                                color = transportModeLine.transportMode.colorCode.hexToComposeColor(),
                                strokeWidth = strokeWidth,
                                circleRadius = circleRadius,
                            )
                            .timeLineTop(
                                color = transportModeLine.transportMode.colorCode.hexToComposeColor(),
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
                modifier = Modifier
                    .timeLineBottom(
                        color = transportModeLine.transportMode.colorCode.hexToComposeColor(),
                        strokeWidth = strokeWidth,
                        circleRadius = circleRadius,
                    )
                    .padding(start = 16.dp, top = 12.dp),
            )
        }
    }
}

@Composable
fun StopInfo(
    time: String,
    name: String,
    isProminent: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = time,
            style = if (isProminent) KrailTheme.typography.bodyMedium else KrailTheme.typography.bodySmall,
            color = KrailTheme.colors.onSurface,
        )
        Text(
            text = name,
            style = if (isProminent) KrailTheme.typography.titleSmall else KrailTheme.typography.bodySmall,
            color = KrailTheme.colors.onSurface,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StopsRow(stops: String, line: TransportModeLine, modifier: Modifier = Modifier) {
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

@PreviewLightDark
@Preview(fontScale = 2f)
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
                ),
                TimeTableState.JourneyCardInfo.Stop(
                    time = "12:30 am",
                    name = "ABC Station, Platform 2",
                ),
                TimeTableState.JourneyCardInfo.Stop(
                    time = "01:00 am",
                    name = "DEF Station, Platform 3",
                ),
            ).toImmutableList(),
            modifier = Modifier.background(KrailTheme.colors.background),
        )
    }
}

@Preview
@Preview(fontScale = 2f)
@Composable
private fun PreviewLegViewTwoStops() {
    KrailTheme {
        LegView(
            duration = "1h 30m",
            routeText = "towards AVC via XYZ",
            transportModeLine = TransportModeLine(
                transportMode = TransportMode.Bus(),
                lineName = "700",
            ),
            stops = listOf(
                TimeTableState.JourneyCardInfo.Stop(
                    time = "12:00 am",
                    name = "XYZ Station, Platform 1",
                ),
                TimeTableState.JourneyCardInfo.Stop(
                    time = "01:00 am",
                    name = "DEF Station, Platform 3",
                ),
            ).toImmutableList(),
            modifier = Modifier.background(KrailTheme.colors.background),
        )
    }
}

@PreviewLightDark
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

@PreviewLightDark
@Composable
private fun PreviewProminentStopInfo() {
    KrailTheme {
        StopInfo(
            time = "12:00",
            name = "XYZ Station, Platform 1",
            isProminent = true,
            modifier = Modifier.background(KrailTheme.colors.background),
        )
    }
}

// endregion
