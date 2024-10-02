package xyz.ksharma.krail.design.system.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.hexToComposeColor
import xyz.ksharma.krail.design.system.model.TransportModeType.Bus
import xyz.ksharma.krail.design.system.model.TransportModeType.Train
import xyz.ksharma.krail.design.system.preview.ComponentPreviews
import xyz.ksharma.krail.design.system.theme.KrailTheme

@Composable
fun JourneyCard(
    modifier: Modifier = Modifier,
    departureText: @Composable RowScope.() -> Unit,
    timeText: @Composable RowScope.() -> Unit,
    transportModeIconRow: @Composable RowScope.() -> Unit,
) {
    BasicJourneyCard(
        modifier = modifier,
        headerRow = {
            departureText()
        },
        secondaryRow = {
            timeText()
        },
        iconsRow = {
            transportModeIconRow()
        },
    )
}

// region Previews

@ComponentPreviews
@Composable
private fun JourneyCardTrainPreview() {
    KrailTheme {
        JourneyCard(
            departureText = {
                Text(
                    text = "in 5 mins on Platform 1",
                    style = KrailTheme.typography.bodyMedium,
                    color = KrailTheme.colors.onSecondaryContainer,
                )
            },
            timeText = {
                Text(
                    text = "8:25am - 8:40am (23 mins)",
                    style = KrailTheme.typography.titleSmall,
                    color = KrailTheme.colors.onSecondaryContainer,
                    modifier = Modifier.alignByBaseline()
                )
            },
            transportModeIconRow = {
                TransportModeIcon(transportModeType = Train)
                TransportModeBadge(
                    backgroundColor = Color.Blue.copy(alpha = 0.8f),
                    badgeText = "T4",
                    modifier = Modifier.padding(start = 8.dp)
                )
            },
        )
    }
}

@ComponentPreviews
@Composable
private fun JourneyCardMultipleModesPreview() {
    KrailTheme {
        JourneyCard(
            departureText = {
                Text(
                    text = "in 5 mins on Platform 1",
                    style = KrailTheme.typography.bodyMedium,
                    color = KrailTheme.colors.onSecondaryContainer,
                )
            },
            timeText = {
                Text(
                    text = "8:25am - 8:40am (23 mins)",
                    style = KrailTheme.typography.titleSmall,
                    color = KrailTheme.colors.onSecondaryContainer,
                    modifier = Modifier.alignByBaseline()
                )
            },
            transportModeIconRow = {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    TransportModeIcon(transportModeType = Train)

                    TransportModeBadge(
                        backgroundColor = Color.Blue.copy(alpha = 0.8f),
                        badgeText = "T4",
                        modifier = Modifier.padding(horizontal = 8.dp),
                    )

                    SeparatorIcon()

                    TransportModeIcon(
                        transportModeType = Bus,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    TransportModeBadge(
                        backgroundColor = Bus.hexColorCode.hexToComposeColor(),
                        badgeText = "700",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            },
        )
    }
}

// endregion
