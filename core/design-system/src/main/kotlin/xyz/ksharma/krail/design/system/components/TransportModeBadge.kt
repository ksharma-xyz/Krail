package xyz.ksharma.krail.design.system.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun TransportModeBadge(
    badgeText: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current

    Box(
        modifier = modifier
            .requiredHeight(height = with (density) { 18.sp.toDp() })
            .clip(shape = RoundedCornerShape(percent = 20))
            .background(color = backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = badgeText,
            color = Color.White,
            // todo - need concrete token for style, meanwhile keep same as TransportModeIcon.
            style = KrailTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier
                .padding(2.dp)
                .wrapContentWidth(),
        )
    }
}

// region Previews

@ComponentPreviews
@Composable
private fun TransportModeBadgeBusPreview() {
    KrailTheme {
        TransportModeBadge(
            badgeText = "700",
            backgroundColor = TransportModeType.Bus.hexColorCode.hexToComposeColor()
        )
    }
}

@ComponentPreviews
@Composable
private fun TransportModeBadgeTrainPreview() {
    KrailTheme {
        TransportModeBadge(
            badgeText = "T1",
            TransportModeType.Train.hexColorCode.hexToComposeColor()
        )
    }
}

@ComponentPreviews
@Composable
private fun TransportModeBadgeFerryPreview() {
    KrailTheme {
        TransportModeBadge(
            badgeText = "F1",
            TransportModeType.Ferry.hexColorCode.hexToComposeColor()
        )
    }
}

// endregion
