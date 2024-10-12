package xyz.ksharma.krail.trip_planner.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.LocalTextStyle
import xyz.ksharma.krail.design.system.components.BasicJourneyCard
import xyz.ksharma.krail.design.system.components.SeparatorIcon
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.preview.ComponentPreviews
import xyz.ksharma.krail.design.system.theme.KrailTheme

@Composable
fun JourneyCard(
    departureText: @Composable RowScope.() -> Unit,
    timeText: @Composable RowScope.() -> Unit,
    transportModeIconRow: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicJourneyCard(
        modifier = modifier,
        content = {
            Row(modifier = Modifier.fillMaxWidth()) {
                CompositionLocalProvider(LocalTextStyle provides KrailTheme.typography.bodyMedium) {
                    departureText()
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                CompositionLocalProvider(LocalTextStyle provides KrailTheme.typography.bodyMedium) {
                    timeText()
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                transportModeIconRow()
            }
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
                    color = KrailTheme.colors.onSecondaryContainer,
                )
            },
            timeText = {
                Text(
                    text = "8:25am - 8:40am (23 mins)",
                    color = KrailTheme.colors.onSecondaryContainer,
                    modifier = Modifier.alignByBaseline()
                )
            },
            transportModeIconRow = {
                TransportModeInfo(
                    letter = 'T',
                    backgroundColor = "#005aa3".hexToComposeColor(),
                    badgeText = "T4",
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
                    color = KrailTheme.colors.onSecondaryContainer,
                )
            },
            timeText = {
                Text(
                    text = "8:25am - 8:40am (23 mins)",
                    color = KrailTheme.colors.onSecondaryContainer,
                    modifier = Modifier.alignByBaseline()
                )
            },
            transportModeIconRow = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TransportModeInfo(
                        letter = 'T',
                        backgroundColor = "#005aa3".hexToComposeColor(),
                        badgeText = "T4",
                    )
                    SeparatorIcon()

                    TransportModeInfo(
                        letter = 'B',
                        backgroundColor = "#00B5EF".hexToComposeColor(),
                        badgeText = "700",
                    )
                }
            },
        )
    }
}

// endregion
