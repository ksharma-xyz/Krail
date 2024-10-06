package xyz.ksharma.krail.trip_planner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.components.TransportModeIcon
import xyz.ksharma.krail.design.system.model.TransportModeType
import xyz.ksharma.krail.design.system.preview.ComponentPreviews
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip_planner.ui.state.searchstop.model.StopItem

@Composable
fun StopSearchListItem(
    stopName: String,
    stopId: String,
    modifier: Modifier = Modifier,
    transportModes: ImmutableSet<xyz.ksharma.krail.trip_planner.domain.model.TransportModeType> = persistentSetOf(),
    onClick: (StopItem) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(role = Role.Button) {
                onClick(
                    StopItem(
                        stopId = stopId,
                        stopName = stopName,
                        transportModes = transportModes.toSet(),
                    )
                )
            }
            .padding(vertical = 8.dp, horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stopName,
            style = KrailTheme.typography.bodyLarge,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            // need to map and convert to Set because SchoolBus and Bus have same logo,
            // but logo should be displayed only once.
            transportModes.map { it.toDisplayModeType() }.toSet().forEach { modeType ->
                TransportModeIcon(transportModeType = modeType)
            }
        }
    }
}

private fun xyz.ksharma.krail.trip_planner.domain.model.TransportModeType.toDisplayModeType(): TransportModeType =
    when (this) {
        xyz.ksharma.krail.trip_planner.domain.model.TransportModeType.Train -> TransportModeType.Train
        xyz.ksharma.krail.trip_planner.domain.model.TransportModeType.Metro -> TransportModeType.Metro
        xyz.ksharma.krail.trip_planner.domain.model.TransportModeType.Ferry -> TransportModeType.Ferry
        xyz.ksharma.krail.trip_planner.domain.model.TransportModeType.Bus -> TransportModeType.Bus
        xyz.ksharma.krail.trip_planner.domain.model.TransportModeType.LightRail -> TransportModeType.LightRail
        xyz.ksharma.krail.trip_planner.domain.model.TransportModeType.SchoolBus -> TransportModeType.Bus
        xyz.ksharma.krail.trip_planner.domain.model.TransportModeType.Coach -> TransportModeType.Coach
    }

// region Preview

@ComponentPreviews
@Composable
private fun StopSearchListItemPreview() {
    KrailTheme {
        StopSearchListItem(
            stopId = "123",
            stopName = "Stop Name",
            transportModes = persistentSetOf(
                xyz.ksharma.krail.trip_planner.domain.model.TransportModeType.Bus,
                xyz.ksharma.krail.trip_planner.domain.model.TransportModeType.LightRail,
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
            stopId = "123",
            stopName = "This is a very long stop name that should wrap to the next line",
            transportModes = persistentSetOf(
                xyz.ksharma.krail.trip_planner.domain.model.TransportModeType.Train,
                xyz.ksharma.krail.trip_planner.domain.model.TransportModeType.Ferry,
            ),
            modifier = Modifier.background(color = KrailTheme.colors.background),
        )
    }
}

// endregion
