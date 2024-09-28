package xyz.ksharma.krail.design.system.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.model.TransportModeType
import xyz.ksharma.krail.design.system.preview.ComponentPreviewLightDark
import xyz.ksharma.krail.design.system.preview.ComponentPreviews
import xyz.ksharma.krail.design.system.theme.KrailTheme

@Composable
fun TransportModeIcon(
    transportModeType: TransportModeType,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .requiredSize(width = 18.dp.toAdaptiveSize(), height = 18.dp.toAdaptiveSize())
            .aspectRatio(1f)
            .background(color = transportModeType.hexColorCode.toColor()),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "${transportModeType.modeName.first()}",
            color = Color.White,
            style = KrailTheme.typography.labelLarge,
        )
    }
}

@Composable
private fun Dp.toAdaptiveSize(): Dp {
    val density = LocalDensity.current
    return when {
        density.fontScale > 1.5f -> this.times(1.6f)
        density.fontScale > 1f -> this.times(density.fontScale)
        else -> this
    }
}

private fun String.toColor(): Color = Color(android.graphics.Color.parseColor(this))

// region Previews

@ComponentPreviewLightDark
@Preview(fontScale = 2f)
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

// endregion
