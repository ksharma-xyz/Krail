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
import xyz.ksharma.krail.design.system.components.BasicJourneyCard
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.preview.ComponentPreviews
import xyz.ksharma.krail.design.system.theme.KrailTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JourneyDetailCard(
    header: String,
    journeyLegList: List<JourneyLeg>,
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
                    backgroundColor = leg.transportation.backgroundColor,
                    letter = leg.transportation.letter,
                    badgeText = leg.transportation.badgeText,
                )

                Text(
                    text = leg.headline,
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
                            stop.departureTime,
                            style = KrailTheme.typography.bodyMedium,
                        )

                        Text(
                            stop.name,
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
                JourneyLeg(
                    headline = "City Circle via Parramatta",
                    summary = "4 Stops (12 min)",
                    transportation = JourneyLeg.Transportation(
                        letter = 'T',
                        badgeText = "T1",
                        backgroundColor = "#f99d1c".hexToComposeColor(),
                    ),
                    stops = listOf(
                        Stop(name = "TownHall Station", departureTime = "8:25am"),
                        Stop(name = "Central Station", departureTime = "8:25am"),
                    )
                ),
                JourneyLeg(
                    headline = "Dessert via Rainy Road",
                    summary = "4 Stops (12 min)",
                    transportation = JourneyLeg.Transportation(
                        letter = 'B',
                        badgeText = "600",
                        backgroundColor = "#f91d1c".hexToComposeColor(),
                    ),
                    stops = listOf(
                        Stop(name = "Umbrella Rd.", departureTime = "8:25am"),
                        Stop(name = "KitKat Rd.", departureTime = "8:25am"),
                    )
                ),
            )
        )
    }
}

// endregion
