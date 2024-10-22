package xyz.ksharma.krail.trip_planner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.preview.PreviewComponent
import xyz.ksharma.krail.design.system.theme.KrailTheme

@Composable
fun TransportModeIcon(
    letter: Char,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    borderEnabled: Boolean = false,
) {
    val density = LocalDensity.current
    val size = with(density) { 18.sp.toDp() }

    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .requiredSize(size)
            .aspectRatio(1f)
            .background(color = backgroundColor)
            .borderIfEnabled(enabled = borderEnabled),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "$letter",
            color = Color.White,
            // todo - need concrete token for style, meanwhile keep same as TransportModeBadge,
            style = KrailTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
        )
    }
}

private fun Modifier.borderIfEnabled(enabled: Boolean): Modifier =
    if (enabled) {
        this.then(Modifier.border(width = 1.dp, color = Color.White, shape = CircleShape))
    } else {
        this
    }

// region Previews

@PreviewComponent
@Composable
private fun TrainPreview() {
    KrailTheme {
        TransportModeIcon(backgroundColor = "#F6891F".hexToComposeColor(), letter = 'T')
    }
}

@PreviewComponent
@Composable
private fun BusPreview() {
    KrailTheme {
        TransportModeIcon(
            backgroundColor = "#00B5EF".hexToComposeColor(),
            letter = 'B',
        )
    }
}

@PreviewComponent
@Composable
private fun MetroPreview() {
    KrailTheme {
        TransportModeIcon(
            backgroundColor = "#009B77".hexToComposeColor(),
            letter = 'M',
        )
    }
}

@PreviewComponent
@Composable
private fun LightRailPreview() {
    KrailTheme {
        TransportModeIcon(
            backgroundColor = "#EE343F".hexToComposeColor(),
            letter = 'L',
        )
    }
}

@PreviewComponent
@Composable
private fun FerryPreview() {
    KrailTheme {
        TransportModeIcon(
            backgroundColor = "#5AB031".hexToComposeColor(),
            letter = 'F',
        )
    }
}

@PreviewComponent
@Composable
private fun TrainWithBackgroundPreview() {
    KrailTheme {
        TransportModeIcon(
            backgroundColor = "#F6891F".hexToComposeColor(),
            letter = 'T',
            borderEnabled = true,
        )
    }
}

@PreviewComponent
@Composable
private fun BusWithBackgroundPreview() {
    KrailTheme {
        TransportModeIcon(
            backgroundColor = "#00B5EF".hexToComposeColor(),
            letter = 'B',
            borderEnabled = true,
        )
    }
}

@PreviewComponent
@Composable
private fun MetroWithBackgroundPreview() {
    KrailTheme {
        TransportModeIcon(
            backgroundColor = "#009B77".hexToComposeColor(),
            letter = 'M',
            borderEnabled = true,
        )
    }
}

@PreviewComponent
@Composable
private fun LightRailWithBackgroundPreview() {
    KrailTheme {
        TransportModeIcon(
            backgroundColor = "#EE343F".hexToComposeColor(),
            letter = 'L',
            borderEnabled = true,
        )
    }
}

@PreviewComponent
@Composable
private fun FerryWithBackgroundPreview() {
    KrailTheme {
        TransportModeIcon(
            backgroundColor = "#5AB031".hexToComposeColor(),
            letter = 'F',
            borderEnabled = true,
        )
    }
}

// endregion
