package xyz.ksharma.krail.trip.planner.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.taj.LocalContentAlpha
import xyz.ksharma.krail.taj.hexToComposeColor
import xyz.ksharma.krail.taj.theme.KrailTheme

@Composable
fun TransportModeInfo(
    letter: Char,
    backgroundColor: Color,
    badgeColor: Color,
    badgeText: String,
    modifier: Modifier = Modifier,
    borderEnabled: Boolean = false,
) {
    // Content alphas should always be 100% for Transport related icons
    CompositionLocalProvider(LocalContentAlpha provides 1f) {
        Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TransportModeIcon(
                letter = letter,
                backgroundColor = backgroundColor,
                borderEnabled = borderEnabled,
            )

            TransportModeBadge(
                backgroundColor = badgeColor,
                badgeText = badgeText,
            )
        }
    }
}

// region Previews

@Composable
private fun TransportModeInfoPreview() {
    KrailTheme {
        TransportModeInfo(
            badgeText = "T4",
            backgroundColor = "#F6891F".hexToComposeColor(),
            badgeColor = "#005aa3".hexToComposeColor(),
            letter = 'T',
        )
    }
}

// endregion
