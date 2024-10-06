package xyz.ksharma.krail.trip_planner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.components.TransportModeIcon
import xyz.ksharma.krail.design.system.model.TransportModeType
import xyz.ksharma.krail.design.system.preview.ComponentPreviews
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip_planner.domain.StopResultMapper
import xyz.ksharma.krail.trip_planner.domain.model.TransportMode

@Composable
fun StopSearchListItem(
    stop: StopResultMapper.StopResult,
    modifier: Modifier = Modifier,
    onClick: (StopResultMapper.StopResult) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stop.stopName,
            style = KrailTheme.typography.bodyLarge,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            stop.mode.map { it.modeType }.forEach { modeType ->
                modeType?.toDisplayModeType()?.let { type ->
                    TransportModeIcon(transportModeType = type)
                }
            }
        }
    }
}

private fun TransportMode.TransportModeType.toDisplayModeType() = when (this) {
    TransportMode.TransportModeType.Bus, TransportMode.TransportModeType.SchoolBus -> TransportModeType.Bus
    TransportMode.TransportModeType.Ferry -> TransportModeType.Ferry
    TransportMode.TransportModeType.LightRail -> TransportModeType.LightRail
    TransportMode.TransportModeType.Metro -> TransportModeType.Metro
    TransportMode.TransportModeType.Train -> TransportModeType.Train
    TransportMode.TransportModeType.Coach -> TransportModeType.Coach
    else -> null
}

// region Preview

@ComponentPreviews
@Composable
private fun StopSearchListItemPreview() {
    KrailTheme {
        StopSearchListItem(
            stop = StopResultMapper.StopResult(
                stopId = "123",
                stopName = "Stop Name",
                mode = listOf(
                    TransportMode(productClassNumber = 1),
                    TransportMode(productClassNumber = 4)
                )
            ),
            modifier = Modifier.background(color = KrailTheme.colors.background),
        )
    }
}


@Preview
@Composable
private fun StopSearchListItemLongNamePreview() {
    KrailTheme {
        StopSearchListItem(
            stop = StopResultMapper.StopResult(
                stopId = "123",
                stopName = "Stop Name - This is a really long stop name",
                mode = listOf(
                    TransportMode(productClassNumber = 1),
                    TransportMode(productClassNumber = 4)
                )
            ),
            modifier = Modifier.background(color = KrailTheme.colors.background),
        )
    }
}

// endregion
