package xyz.ksharma.krail.trip_planner.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import xyz.ksharma.krail.design.system.components.BasicJourneyCard
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip_planner.ui.R
import xyz.ksharma.krail.trip_planner.ui.state.TransportMode
import xyz.ksharma.krail.trip_planner.ui.state.TransportModeLine
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JourneyDetailCard(
    timeToDeparture: String,
    platformNumber: Char,
    totalTravelTime: String,
    legList: ImmutableList<TimeTableState.JourneyCardInfo.Leg>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    // todo can be reusable logic for consistent icon size
    val iconSize = with(density) { 14.sp.toDp() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(KrailTheme.colors.surface)
            .padding(vertical = 12.dp)
            .clickable(role = Role.Button) { onClick() }
    ) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "In $timeToDeparture",
                style = KrailTheme.typography.titleLarge,
                color = legList.first().transportModeLine.transportMode.colorCode.hexToComposeColor()
            )
            Text(
                text = "Platform $platformNumber",
                style = KrailTheme.typography.titleLarge,
                color = legList.first().transportModeLine.transportMode.colorCode.hexToComposeColor()
            )
        }

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
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
                    modifier = Modifier.padding(start = 4.dp)
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
                    colorFilter = ColorFilter.tint(color = KrailTheme.colors.onBackground),
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .align(Alignment.CenterVertically)
                        .size(iconSize),
                )
                Text(
                    text = totalTravelTime, style = KrailTheme.typography.bodyLarge,
                )
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        legList.forEach { leg ->
            if (leg.walkInterchange?.position == TimeTableState.JourneyCardInfo.WalkPosition.BEFORE) {
                leg.walkInterchange?.duration?.let { duration ->
                    WalkingLeg(
                        duration = duration,
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            JourneyLeg(
                transportModeLine = leg.transportModeLine,
                stopsInfo = leg.stopsInfo,
                departureTime = leg.stops.first().time,
                stopName = leg.stops.first().name,
                isWheelchairAccessible = false,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            if (leg.walkInterchange?.position == TimeTableState.JourneyCardInfo.WalkPosition.AFTER) {
                leg.walkInterchange?.duration?.let { duration ->
                    WalkingLeg(
                        duration = duration,
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewJourneyDetailCard() {
    KrailTheme {
        JourneyDetailCard(
            timeToDeparture = "5 mins",
            platformNumber = '1',
            totalTravelTime = "1h 30mins",
            legList = listOf(
                TimeTableState.JourneyCardInfo.Leg(
                    transportModeLine = TransportModeLine(
                        transportMode = TransportMode.Bus(),
                        lineName = "600",
                    ),
                    displayText = "Dessert via Rainy Road",
                    stopsInfo = "4 stops (12 min)",
                    stops = listOf(
                        TimeTableState.JourneyCardInfo.Stop(
                            name = "TownHall Station",
                            time = "8:25am",
                        ),
                        TimeTableState.JourneyCardInfo.Stop(
                            name = "Central Station",
                            time = "8:30am"
                        ),
                    ).toImmutableList()
                ),
            ).toImmutableList(),
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewJourneyDetailCardWalkBefore() {
    KrailTheme {
        JourneyDetailCard(
            timeToDeparture = "5 mins",
            platformNumber = '1',
            totalTravelTime = "1h 30mins",
            legList = listOf(
                TimeTableState.JourneyCardInfo.Leg(
                    walkInterchange = TimeTableState.JourneyCardInfo.WalkInterchange(
                        duration = "5 mins",
                        position = TimeTableState.JourneyCardInfo.WalkPosition.BEFORE,
                    ),
                    transportModeLine = TransportModeLine(
                        transportMode = TransportMode.Bus(),
                        lineName = "600",
                    ),
                    displayText = "Dessert via Rainy Road",
                    stopsInfo = "4 stops (12 min)",
                    stops = listOf(
                        TimeTableState.JourneyCardInfo.Stop(
                            name = "TownHall Station",
                            time = "8:25am",
                        ),
                        TimeTableState.JourneyCardInfo.Stop(
                            name = "Central Station",
                            time = "8:30am"
                        ),
                    ).toImmutableList()
                ),
            ).toImmutableList(),
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewJourneyDetailCardWalkAfter() {
    KrailTheme {
        JourneyDetailCard(
            timeToDeparture = "5 mins",
            platformNumber = '1',
            totalTravelTime = "1h 30mins",
            legList = listOf(
                TimeTableState.JourneyCardInfo.Leg(
                    walkInterchange = TimeTableState.JourneyCardInfo.WalkInterchange(
                        duration = "5 mins",
                        position = TimeTableState.JourneyCardInfo.WalkPosition.AFTER,
                    ),
                    transportModeLine = TransportModeLine(
                        transportMode = TransportMode.Bus(),
                        lineName = "600",
                    ),
                    displayText = "Dessert via Rainy Road",
                    stopsInfo = "4 stops (12 min)",
                    stops = listOf(
                        TimeTableState.JourneyCardInfo.Stop(
                            name = "TownHall Station",
                            time = "8:25am",
                        ),
                        TimeTableState.JourneyCardInfo.Stop(
                            name = "Central Station",
                            time = "8:30am"
                        ),
                    ).toImmutableList()
                ),
            ).toImmutableList(),
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, fontScale = 2f)
@Composable
private fun PreviewMultiLegJourneyDetailCard() {
    KrailTheme {
        JourneyDetailCard(
            timeToDeparture = "5 mins",
            platformNumber = '1',
            totalTravelTime = "1h 30mins",
            legList = listOf(
                TimeTableState.JourneyCardInfo.Leg(
                    walkInterchange = TimeTableState.JourneyCardInfo.WalkInterchange(
                        duration = "5 mins",
                        position = TimeTableState.JourneyCardInfo.WalkPosition.AFTER,
                    ),
                    transportModeLine = TransportModeLine(
                        transportMode = TransportMode.Bus(),
                        lineName = "600",
                    ),
                    displayText = "Dessert via Rainy Road",
                    stopsInfo = "4 stops (12 min)",
                    stops = listOf(
                        TimeTableState.JourneyCardInfo.Stop(
                            name = "TownHall Station",
                            time = "8:25am",
                        ),
                        TimeTableState.JourneyCardInfo.Stop(
                            name = "Central Station",
                            time = "8:30am"
                        ),
                    ).toImmutableList()
                ),
                TimeTableState.JourneyCardInfo.Leg(
                    transportModeLine = TransportModeLine(
                        transportMode = TransportMode.Train(),
                        lineName = "T5",
                    ),
                    displayText = "EmuPlains via Forest Hills",
                    stopsInfo = "2 stops (30 min)",
                    stops = listOf(
                        TimeTableState.JourneyCardInfo.Stop(
                            name = "Central Station",
                            time = "8:32am",
                        ),
                        TimeTableState.JourneyCardInfo.Stop(
                            name = "Forest Hills Station",
                            time = "9:00am"
                        ),
                    ).toImmutableList()
                )
            ).toImmutableList(),
            onClick = {},
        )
    }
}

/*
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JourneyDetailCard(
    header: String,
    journeyLegList: List<TimeTableState.JourneyCardInfo.Leg>,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    BasicJourneyCard(modifier = modifier.clickable(role = Role.Button, onClick = onClick)) {
        // TODO - consider meaningful text breaks in lines. "Platform" and Platform number text
        //  should be on same line.
        Text(header, style = KrailTheme.typography.titleMedium)

        journeyLegList.forEach { leg ->
            FlowRow(
                modifier = Modifier.padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                TransportModeInfo(
                    backgroundColor = leg.transportModeLine.transportMode.colorCode.hexToComposeColor(),
                    letter = leg.transportModeLine.transportMode.name.first(),
                    badgeText = leg.transportModeLine.lineName,
                    badgeColor = leg.transportModeLine.lineColorCode.hexToComposeColor(),
                )

                Text(
                    text = leg.displayText,
                    color = KrailTheme.colors.onPrimaryContainer,
                    style = KrailTheme.typography.bodyMedium,
                )
            }

            Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                leg.stops.forEach { stop ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = stop.time,
                            style = KrailTheme.typography.bodyMedium,
                        )

                        Text(
                            text = stop.name,
                            style = KrailTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}*/
