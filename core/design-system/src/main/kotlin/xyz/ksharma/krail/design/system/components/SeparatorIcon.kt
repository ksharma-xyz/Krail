package xyz.ksharma.krail.design.system.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.preview.ComponentPreviews
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.design.system.toAdaptiveSize

@Composable
fun SeparatorIcon(modifier: Modifier = Modifier, color: Color = KrailTheme.colors.onSurface) {
    Box(
        modifier = modifier
            .size(4.dp.toAdaptiveSize())
            .clip(CircleShape)
            .background(color = color)
            .padding(end = 8.dp),
    )
}

// region Previews

@ComponentPreviews
@Composable
private fun SeparatorIconPreview() {
    KrailTheme {
        Box(modifier = Modifier
            .background(KrailTheme.colors.surface)
            .padding(10.dp)) {
            SeparatorIcon()
        }
    }
}

// endregion
