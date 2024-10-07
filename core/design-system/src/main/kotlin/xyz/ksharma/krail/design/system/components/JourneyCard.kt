package xyz.ksharma.krail.design.system.components

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
import xyz.ksharma.krail.design.system.model.TransportModeLine
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
                TransportModeInfo(transportModeLine = TransportModeLine(Train, "T4", "#005aa3"))
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

                    TransportModeInfo(transportModeLine = TransportModeLine(Train, "T4", "#005aa3"))

                    SeparatorIcon()

                    TransportModeInfo(transportModeLine = TransportModeLine(Bus, "700", "#00B5EF"))
                }
            },
        )
    }
}

// endregion
