package xyz.ksharma.krail.trip.planner.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.design.system.toAdaptiveSize
import xyz.ksharma.krail.trip.planner.ui.R
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.TransportModeLine

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JourneyLeg(
    transportModeLine: TransportModeLine,
    stopsInfo: String,
    departureTime: String,
    stopName: String,
    modifier: Modifier = Modifier,
    isWheelchairAccessible: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(KrailTheme.colors.surface) // for better color contrast
            .background(
                color = transportModeLine.transportMode.colorCode
                    .hexToComposeColor()
                    .copy(alpha = if (isSystemInDarkTheme()) 0.7f else 0.15f),
            )
            .padding(vertical = 8.dp, horizontal = 8.dp),
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            TransportModeInfo(
                letter = transportModeLine.transportMode.name.first(),
                backgroundColor = transportModeLine.transportMode.colorCode.hexToComposeColor(),
                badgeText = transportModeLine.lineName,
                badgeColor = transportModeLine.lineColorCode.hexToComposeColor(),
                borderEnabled = isSystemInDarkTheme(),
                modifier = Modifier.align(Alignment.CenterVertically),
            )
            Text(
                text = stopsInfo,
                modifier = Modifier.align(Alignment.CenterVertically),
                style = KrailTheme.typography.title,
                color = if (isSystemInDarkTheme()) {
                    KrailTheme.colors.onSurface
                } else {
                    transportModeLine.transportMode.colorCode.hexToComposeColor()
                },
            )
        }
        FlowRow(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Row(
                modifier = Modifier.align(Alignment.CenterVertically),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val density = LocalDensity.current
                val size = with(density) { 18.sp.toDp() }
                Image(
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = null,
                    modifier = Modifier
                        .size(size)
                        .align(Alignment.CenterVertically),
                    colorFilter = ColorFilter.tint(
                        color = if (isSystemInDarkTheme()) {
                            KrailTheme.colors.onSurface
                        } else {
                            transportModeLine.transportMode.colorCode.hexToComposeColor()
                                .copy(alpha = 0.9f)
                        },
                    ),
                )
                Text(
                    text = departureTime,
                    style = KrailTheme.typography.bodyMedium,
                    color = if (isSystemInDarkTheme()) KrailTheme.colors.surface else KrailTheme.colors.onSurface,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp),
                )
            }
            Row(
                modifier = Modifier.align(Alignment.CenterVertically),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = stopName,
                    style = KrailTheme.typography.titleSmall,
                    color = if (isSystemInDarkTheme()) KrailTheme.colors.surface else KrailTheme.colors.onSurface,
                    modifier = Modifier.align(Alignment.CenterVertically),
                )
                if (isWheelchairAccessible) {
                    Image(
                        painter = painterResource(R.drawable.ic_a11y),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(
                            color = KrailTheme.colors.onSurface,
                        ),
                        modifier = Modifier
                            .size(14.dp.toAdaptiveSize())
                            .align(Alignment.CenterVertically),
                    )
                }
            }
        }
    }
}

@PreviewLightDark
// @Preview(fontScale = 2f)
@Composable
private fun PreviewJourneyLegBus() {
    KrailTheme {
        JourneyLeg(
            transportModeLine = TransportModeLine(
                transportMode = TransportMode.Bus(),
                lineName = "700",
            ),
            stopsInfo = "2 stops (1h 10mins)",
            departureTime = "8:25am",
            stopName = "Central Station",
            isWheelchairAccessible = true,
        )
    }
}

// @PreviewLightDark
@Composable
private fun PreviewJourneyLegTrain() {
    KrailTheme {
        JourneyLeg(
            transportModeLine = TransportModeLine(
                transportMode = TransportMode.Train(),
                lineName = "T2",
            ),
            stopsInfo = "2 stops (1h 10mins)",
            departureTime = "8:25am",
            stopName = "Central Station",
            isWheelchairAccessible = true,
        )
    }
}

// @PreviewLightDark
@Composable
private fun PreviewJourneyLegMetro() {
    KrailTheme {
        JourneyLeg(
            transportModeLine = TransportModeLine(
                transportMode = TransportMode.Metro(),
                lineName = "M1",
            ),
            stopsInfo = "2 stops (1h 10mins)",
            departureTime = "8:25am",
            stopName = "Central Station",
            isWheelchairAccessible = true,
        )
    }
}
