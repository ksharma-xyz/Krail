package xyz.ksharma.krail.design.system.components

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
import xyz.ksharma.krail.design.system.hexToComposeColor
import xyz.ksharma.krail.design.system.model.TransportModeType
import xyz.ksharma.krail.design.system.preview.ComponentPreviews
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.design.system.toAdaptiveSize

@Composable
fun TransportModeIcon(
    transportModeType: TransportModeType,
    modifier: Modifier = Modifier,
    borderEnabled: Boolean = false,
) {
    val density = LocalDensity.current
    val size = with (density) { 18.sp.toDp() }

    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .requiredSize(size)
            .aspectRatio(1f)
            .background(color = transportModeType.hexColorCode.hexToComposeColor())
            .borderIfEnabled(enabled = borderEnabled),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "${transportModeType.modeName.first()}",
            color = Color.White,
            // todo - need concrete token for style, meanwhile keep same as TransportModeBadge,
            style = KrailTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
        )
    }
}

private fun Modifier.borderIfEnabled(enabled: Boolean): Modifier = if (enabled) {
    this.then(Modifier.border(width = 1.dp, color = Color.White, shape = CircleShape))
} else this

// region Previews

@ComponentPreviews
@Composable
private fun TrainPreview() {
    KrailTheme {
        TransportModeIcon(TransportModeType.Train)
    }
}

@ComponentPreviews
@Composable
private fun BusPreview() {
    KrailTheme {
        TransportModeIcon(TransportModeType.Bus)
    }
}

@ComponentPreviews
@Composable
private fun MetroPreview() {
    KrailTheme {
        TransportModeIcon(TransportModeType.Metro)
    }
}

@ComponentPreviews
@Composable
private fun LightRailPreview() {
    KrailTheme {
        TransportModeIcon(TransportModeType.LightRail)
    }
}

@ComponentPreviews
@Composable
private fun FerryPreview() {
    KrailTheme {
        TransportModeIcon(TransportModeType.Ferry)
    }
}

@ComponentPreviews
@Composable
private fun TrainWithBackgroundPreview() {
    KrailTheme {
        TransportModeIcon(TransportModeType.Train, borderEnabled = true)
    }
}

@ComponentPreviews
@Composable
private fun BusWithBackgroundPreview() {
    KrailTheme {
        TransportModeIcon(TransportModeType.Bus, borderEnabled = true)
    }
}

@ComponentPreviews
@Composable
private fun MetroWithBackgroundPreview() {
    KrailTheme {
        TransportModeIcon(TransportModeType.Metro, borderEnabled = true)
    }
}

@ComponentPreviews
@Composable
private fun LightRailWithBackgroundPreview() {
    KrailTheme {
        TransportModeIcon(TransportModeType.LightRail, borderEnabled = true)
    }
}

@ComponentPreviews
@Composable
private fun FerryWithBackgroundPreview() {
    KrailTheme {
        TransportModeIcon(TransportModeType.Ferry, borderEnabled = true)
    }
}

// endregion
