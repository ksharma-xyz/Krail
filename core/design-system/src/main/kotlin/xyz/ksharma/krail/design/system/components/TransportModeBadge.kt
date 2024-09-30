package xyz.ksharma.krail.design.system.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.hexToComposeColor
import xyz.ksharma.krail.design.system.model.TransportModeType
import xyz.ksharma.krail.design.system.preview.ComponentPreviews
import xyz.ksharma.krail.design.system.theme.KrailTheme

@Composable
fun TransportModeBadge(
    badgeText: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(percent = 20))
            .background(color = backgroundColor),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = badgeText,
            color = Color.White,
            style = KrailTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), // todo - need concrete token for this
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
