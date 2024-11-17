package xyz.ksharma.krail.trip.planner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.ksharma.krail.taj.LocalContentAlpha
import xyz.ksharma.krail.taj.components.Text
import xyz.ksharma.krail.taj.theme.KrailTheme

@Composable
fun TransportModeIcon(
    letter: Char,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    borderEnabled: Boolean = false,
    iconSize: TextUnit = 18.sp,
    fontSize: TextUnit? = null,
) {
    val density = LocalDensity.current
    val textStyle = KrailTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium)

    // Content alphas should always be 100% for Transport related icons
    CompositionLocalProvider(LocalContentAlpha provides 1f) {
        Box(
            modifier = modifier
                .clip(shape = CircleShape)
                .requiredSize(with(density) { iconSize.toDp() })
                .aspectRatio(1f)
                .background(color = backgroundColor)
                .borderIfEnabled(enabled = borderEnabled),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "$letter",
                color = Color.White,
                // todo - need concrete token for style, meanwhile keep same as TransportModeBadge,
                style = textStyle.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = fontSize ?: textStyle.fontSize,
                ),
            )
        }
    }
}

private fun Modifier.borderIfEnabled(enabled: Boolean): Modifier =
    if (enabled) {
        this.then(Modifier.border(width = 1.dp, color = Color.White, shape = CircleShape))
    } else {
        this
    }

// region Previews


@Composable
private fun TrainPreview() {
    KrailTheme {
        TransportModeIcon(backgroundColor = "#F6891F".hexToComposeColor(), letter = 'T')
    }
}


@Composable
private fun BusPreview() {
    KrailTheme {
        TransportModeIcon(
            backgroundColor = "#00B5EF".hexToComposeColor(),
            letter = 'B',
        )
    }
}


@Composable
private fun MetroPreview() {
    KrailTheme {
        TransportModeIcon(
            backgroundColor = "#009B77".hexToComposeColor(),
            letter = 'M',
        )
    }
}


@Composable
private fun LightRailPreview() {
    KrailTheme {
        TransportModeIcon(
            backgroundColor = "#EE343F".hexToComposeColor(),
            letter = 'L',
        )
    }
}


@Composable
private fun FerryPreview() {
    KrailTheme {
        TransportModeIcon(
            backgroundColor = "#5AB031".hexToComposeColor(),
            letter = 'F',
        )
    }
}


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
