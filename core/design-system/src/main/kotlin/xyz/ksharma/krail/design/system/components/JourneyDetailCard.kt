package xyz.ksharma.krail.design.system.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.hexToComposeColor
import xyz.ksharma.krail.design.system.model.JourneyLeg
import xyz.ksharma.krail.design.system.model.Stop
import xyz.ksharma.krail.design.system.model.TransportModeLine
import xyz.ksharma.krail.design.system.model.TransportModeType
import xyz.ksharma.krail.design.system.preview.ComponentPreviews
import xyz.ksharma.krail.design.system.theme.KrailTheme


@Composable
fun JourneyDetailCard(
    modifier: Modifier = Modifier,
    header: String,
    journeyLegList: List<JourneyLeg>,
) {
    BasicJourneyCard(modifier = modifier) {
        // TODO - consider meaningful text breaks in lines. "Platform" and Platform number text
        //  should be on same line.
        Text(header, style = KrailTheme.typography.titleMedium)

        journeyLegList.forEach { leg ->
            Row(
                modifier = Modifier.padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TransportModeIcon(
                        transportModeType = leg.transportLine.transportModeType,
                    )

                    TransportModeBadge(
                        backgroundColor = leg.transportLine.lineHexColorCode.hexToComposeColor(),
                        badgeText = leg.transportLine.lineName,
                    )
                }

                // TODO - Flow to next line instead of same Row for large font size/Measure.
                Text(
                    leg.headline,
                    style = KrailTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
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
            header = "in 5 mins on Platform 1 (10 min)", journeyLegList = listOf(
                JourneyLeg(
                    headline = "City Circle via Parramatta",
                    summary = "4 Stops (12 min)",
                    transportLine = TransportModeLine(
                        transportModeType = TransportModeType.Train,
                        lineName = "T1",
                        lineHexColorCode = "#f99d1c",
                    ),
                    stops = listOf(
                        Stop(name = "TownHall Station", departureTime = "8:25am"),
                        Stop(name = "Central Station", departureTime = "8:25am"),
                    )
                ),
                JourneyLeg(
                    headline = "Dessert via Rainy Road",
                    summary = "4 Stops (12 min)",
                    transportLine = TransportModeLine(
                        transportModeType = TransportModeType.Bus,
                        lineName = "600",
                        lineHexColorCode = "#f91d1c",
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
