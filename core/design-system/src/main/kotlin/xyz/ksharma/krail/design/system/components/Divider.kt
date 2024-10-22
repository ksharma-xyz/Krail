package xyz.ksharma.krail.design.system.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.LocalContentColor
import xyz.ksharma.krail.design.system.theme.KrailTheme

@Composable
fun Divider(
    modifier: Modifier = Modifier,
    type: DividerType = DividerType.HORIZONTAL,
    color: Color? = null,
) {
    CompositionLocalProvider(LocalContentColor provides KrailTheme.colors.onBackground.copy(alpha = 0.2f)) {
        Box(
            modifier = modifier
                .then(
                    when (type) {
                        DividerType.HORIZONTAL ->
                            Modifier
                                .fillMaxWidth()
                                .height(1.dp)

                        DividerType.VERTICAL ->
                            Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                    },
                )
                .background(color = color ?: LocalContentColor.current),
        )
    }
}

enum class DividerType {
    HORIZONTAL,
    VERTICAL,
}

@PreviewLightDark
@Composable
private fun DividerPreview() {
    KrailTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(KrailTheme.colors.background),
            contentAlignment = Alignment.Center,
        ) {
            Divider()
        }
    }
}

@PreviewLightDark
@Composable
private fun DividerVerticalPreview() {
    KrailTheme {
        Box(
            modifier = Modifier
                .size(30.dp)
                .background(KrailTheme.colors.background),
            contentAlignment = Alignment.Center,
        ) {
            Divider(type = DividerType.VERTICAL)
        }
    }
}
