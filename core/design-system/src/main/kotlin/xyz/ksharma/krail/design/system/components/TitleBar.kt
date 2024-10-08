package xyz.ksharma.krail.design.system.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.LocalTextColor
import xyz.ksharma.krail.design.system.LocalTextStyle
import xyz.ksharma.krail.design.system.preview.ComponentPreviews
import xyz.ksharma.krail.design.system.theme.KrailTheme

@Composable
fun TitleBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp)
    ) {
        CompositionLocalProvider(
            LocalTextColor provides KrailTheme.colors.onBackground,
            LocalTextStyle provides KrailTheme.typography.headlineMedium,
        ) {
            title()
        }
    }
}

// region Previews

@ComponentPreviews
@Composable
private fun TitleBarPreview() {
    KrailTheme {
        TitleBar(
            title = {
                Text(text = "Title")
            },
            modifier = Modifier.background(color = KrailTheme.colors.background)
        )
    }
}

// endregion
