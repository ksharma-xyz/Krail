package xyz.ksharma.krail.design.system.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.hexToComposeColor
import xyz.ksharma.krail.design.system.model.TransportModeLine
import xyz.ksharma.krail.design.system.model.TransportModeType.Train
import xyz.ksharma.krail.design.system.theme.KrailTheme

@Composable
fun TransportModeInfo(modifier: Modifier = Modifier, transportModeLine: TransportModeLine) {

    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {

        TransportModeIcon(transportModeType = transportModeLine.transportModeType)

        TransportModeBadge(
            backgroundColor = transportModeLine.lineHexColorCode.hexToComposeColor(),
            badgeText = transportModeLine.lineName,
        )
    }
}

// region Previews

@Preview
@Composable
private fun TransportModeInfoPreview() {
    KrailTheme {
        TransportModeInfo(transportModeLine = TransportModeLine(Train, "T4", "#005aa3"))
    }
}

// endregion
