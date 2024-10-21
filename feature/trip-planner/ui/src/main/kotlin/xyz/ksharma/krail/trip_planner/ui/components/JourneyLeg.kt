package xyz.ksharma.krail.trip_planner.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.design.system.toAdaptiveSize
import xyz.ksharma.krail.trip_planner.ui.R
import xyz.ksharma.krail.trip_planner.ui.state.TransportMode
import xyz.ksharma.krail.trip_planner.ui.state.TransportModeLine

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JourneyLeg(
    transportModeLine: TransportModeLine,
    stopsInfo: String,
    departureTime: String,
    stopName: String,
    isWheelchairAccessible: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = transportModeLine.transportMode.colorCode
                    .hexToComposeColor()
                    .copy(alpha = 0.1f)
            )
            .padding(vertical = 8.dp, horizontal = 8.dp),
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            TransportModeInfo(
                letter = transportModeLine.transportMode.name.first(),
                backgroundColor = transportModeLine.transportMode.colorCode.hexToComposeColor(),
                badgeText = transportModeLine.lineName,
                badgeColor = transportModeLine.lineColorCode.hexToComposeColor(),
                modifier = Modifier.align(Alignment.CenterVertically),
            )
            Text(
                text = stopsInfo,
                modifier = Modifier.align(Alignment.CenterVertically),
                style = KrailTheme.typography.title,
                color = transportModeLine.transportMode.colorCode.hexToComposeColor(),
            )
        }
        FlowRow(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.align(Alignment.CenterVertically),
                verticalAlignment = Alignment.CenterVertically
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
                        color = transportModeLine.transportMode.colorCode.hexToComposeColor()
                            .copy(alpha = 0.9f)
                    ),
                )
                Text(
                    text = departureTime,
                    style = KrailTheme.typography.bodyMedium,
                    color = Color(0xFF1A1A1A),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp),
                )
            }
            Row(
                modifier = Modifier.align(Alignment.CenterVertically),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stopName,
                    style = KrailTheme.typography.titleSmall,
                    color = Color(0xFF1A1A1A),
                    modifier = Modifier.align(Alignment.CenterVertically),
                )
                if (isWheelchairAccessible) {
                    Image(
                        painter = painterResource(R.drawable.ic_a11y),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = Color(0xFF1A1A1A)),
                        modifier = Modifier
                            .size(14.dp.toAdaptiveSize())
                            .align(Alignment.CenterVertically),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(showBackground = true, fontScale = 2f)
@Composable
private fun PreviewJourneyLeg() {
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
