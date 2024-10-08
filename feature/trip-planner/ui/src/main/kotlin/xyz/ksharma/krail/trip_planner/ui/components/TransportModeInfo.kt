package xyz.ksharma.krail.trip_planner.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.theme.KrailTheme

@Composable
fun TransportModeInfo(
    letter: Char,
    backgroundColor: Color,
    badgeText: String,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {

        TransportModeIcon(letter = letter, backgroundColor = backgroundColor)

        TransportModeBadge(
            backgroundColor = backgroundColor,
            badgeText = badgeText,
        )
    }
}

// region Previews

@Preview
@Composable
private fun TransportModeInfoPreview() {
    KrailTheme {
        TransportModeInfo(
            badgeText = "T4",
            backgroundColor = "#F6891F".hexToComposeColor(),
            letter = 'T'
        )
    }
}

// endregion
