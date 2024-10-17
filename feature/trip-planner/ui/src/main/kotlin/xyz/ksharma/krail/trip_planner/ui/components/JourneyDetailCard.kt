package xyz.ksharma.krail.trip_planner.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toImmutableList
import xyz.ksharma.krail.design.system.components.BasicJourneyCard
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.preview.ComponentPreviews
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip_planner.ui.state.TransportMode
import xyz.ksharma.krail.trip_planner.ui.state.TransportModeLine
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableState

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
                    badgeColor = leg.transportModeLine.lineColorCode?.hexToComposeColor(),
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
}

// region Previews

@ComponentPreviews
@Composable
private fun JourneyDetailCardPreview(modifier: Modifier = Modifier) {
    KrailTheme {
        JourneyDetailCard(
            header = "in 5 mins on Platform 1 (10 min)",
            journeyLegList = listOf(
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
                            time = "8:25am"
                        ),
                    ).toImmutableList()
                ),
                TimeTableState.JourneyCardInfo.Leg(
                    transportModeLine = TransportModeLine(
                        transportMode = TransportMode.Train(),
                        lineName = "T4",
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
                            time = "8:25am"
                        ),
                    ).toImmutableList()
                ),
            )
        )
    }
}

// endregion
