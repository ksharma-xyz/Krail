package xyz.ksharma.krail.trip.planner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.taj.LocalTextColor
import xyz.ksharma.krail.taj.LocalTextStyle
import xyz.ksharma.krail.taj.components.Text
import xyz.ksharma.krail.taj.hexToComposeColor
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.taj.toAdaptiveDecorativeIconSize
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode

@Composable
fun TransportModeIcon(
    transportMode: TransportMode,
    modifier: Modifier = Modifier,
    borderColor: Color = Color.White,
    textColor: Color = Color.White,
    displayBorder: Boolean = false,
    size: TransportModeIconSize = TransportModeIconSize.Small,
) {
    CompositionLocalProvider(
        LocalTextColor provides textColor,
        LocalTextStyle provides KrailTheme.typography.titleMedium,
    ) {
        Box(
            modifier = modifier
                .size(size.dpSize.toAdaptiveDecorativeIconSize())
                .clip(CircleShape)
                .background(
                    color = transportMode.colorCode.hexToComposeColor(),
                    shape = CircleShape,
                )
                .borderIfEnabled(
                    enabled = displayBorder,
                    color = borderColor,
                ),
            contentAlignment = Alignment.Center,
        ) {
            CompositionLocalProvider(
                LocalTextColor provides Color.White,
            ) {
                Text(
                    text = transportMode.name.first().toString().uppercase(),
                    color = Color.White,
                )
            }
        }
    }
}

@Composable
private fun Modifier.borderIfEnabled(enabled: Boolean, color: Color): Modifier =
    if (enabled) {
        this.then(
            border(
                width = 3.dp.toAdaptiveDecorativeIconSize(),
                color = color,
                shape = CircleShape,
            )
        )
    } else this

enum class TransportModeIconSize(val dpSize: Dp) {
    Small(24.dp), Medium(28.dp), Large(32.dp)
}

// region Previews

@Composable
private fun TrainPreview() {
    KrailTheme {
        TransportModeIcon(
            transportMode = TransportMode.Train(),
            borderColor = Color.White,
            textColor = Color.White,
            displayBorder = false
        )
    }
}

@Composable
private fun BusPreview() {
    KrailTheme {
        TransportModeIcon(
            transportMode = TransportMode.Bus(),
            borderColor = Color.White,
            textColor = Color.White,
            displayBorder = false
        )
    }
}

@Composable
private fun MetroPreview() {
    KrailTheme {
        TransportModeIcon(
            transportMode = TransportMode.Metro(),
            borderColor = Color.White,
            textColor = Color.White,
            displayBorder = false
        )
    }
}

@Composable
private fun LightRailPreview() {
    KrailTheme {
        TransportModeIcon(
            transportMode = TransportMode.LightRail(),
            borderColor = Color.White,
            textColor = Color.White,
            displayBorder = false
        )
    }
}

@Composable
private fun FerryPreview() {
    KrailTheme {
        TransportModeIcon(
            transportMode = TransportMode.Ferry(),
            borderColor = Color.White,
            textColor = Color.White,
            displayBorder = false
        )
    }
}

@Composable
private fun TrainWithBackgroundPreview() {
    KrailTheme {
        TransportModeIcon(
            transportMode = TransportMode.Train(),
            borderColor = Color.White,
            textColor = Color.White,
            displayBorder = true
        )
    }
}

@Composable
private fun BusWithBackgroundPreview() {
    KrailTheme {
        TransportModeIcon(
            transportMode = TransportMode.Bus(),
            borderColor = Color.White,
            textColor = Color.White,
            displayBorder = true
        )
    }
}

@Composable
private fun MetroWithBackgroundPreview() {
    KrailTheme {
        TransportModeIcon(
            transportMode = TransportMode.Metro(),
            borderColor = Color.White,
            textColor = Color.White,
            displayBorder = true
        )
    }
}

@Composable
private fun LightRailWithBackgroundPreview() {
    KrailTheme {
        TransportModeIcon(
            transportMode = TransportMode.LightRail(),
            borderColor = Color.White,
            textColor = Color.White,
            displayBorder = true
        )
    }
}

@Composable
private fun FerryWithBackgroundPreview() {
    KrailTheme {
        TransportModeIcon(
            transportMode = TransportMode.Ferry(),
            borderColor = Color.White,
            textColor = Color.White,
            displayBorder = true,
        )
    }
}

// endregion
