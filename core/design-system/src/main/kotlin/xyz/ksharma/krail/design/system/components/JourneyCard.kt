package xyz.ksharma.krail.design.system.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            },
        )
    }
}
