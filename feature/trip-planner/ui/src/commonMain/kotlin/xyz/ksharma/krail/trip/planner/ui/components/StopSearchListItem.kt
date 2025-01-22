package xyz.ksharma.krail.trip.planner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import xyz.ksharma.krail.taj.components.Text
import xyz.ksharma.krail.taj.hexToComposeColor
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.searchstop.model.StopItem

@Composable
fun StopSearchListItem(
    stopName: String,
    stopId: String,
    transportModeSet: ImmutableSet<TransportMode>,
    textColor: Color,
    modifier: Modifier = Modifier,
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
                    ),
                )
            }
            .padding(vertical = 8.dp, horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = stopName,
            color = textColor,
            style = KrailTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            transportModeSet.forEach { mode ->
                TransportModeIcon(
                    letter = mode.name.first(),
                    backgroundColor = mode.colorCode.hexToComposeColor(),
                )
            }
        }
    }
}

// region Preview

@Composable
private fun StopSearchListItemPreview() {
    KrailTheme {
        StopSearchListItem(
            stopId = "123",
            stopName = "Stop Name",
            transportModeSet = persistentSetOf(
                TransportMode.Bus(),
                TransportMode.LightRail(),
            ),
            textColor = KrailTheme.colors.onSurface,
            modifier = Modifier.background(color = KrailTheme.colors.surface),
        )
    }
}

@Composable
private fun StopSearchListItemLongNamePreview() {
    KrailTheme {
        StopSearchListItem(
            stopId = "123",
            stopName = "This is a very long stop name that should wrap to the next line",
            transportModeSet = persistentSetOf(
                TransportMode.Train(),
                TransportMode.Ferry(),
            ),
            textColor = KrailTheme.colors.onSurface,
            modifier = Modifier.background(color = KrailTheme.colors.surface),
        )
    }
}

// endregion
