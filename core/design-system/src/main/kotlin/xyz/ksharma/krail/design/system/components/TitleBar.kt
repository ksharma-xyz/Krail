package xyz.ksharma.krail.design.system.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.LocalContentColor
import xyz.ksharma.krail.design.system.LocalTextColor
import xyz.ksharma.krail.design.system.LocalTextStyle
import xyz.ksharma.krail.design.system.R
import xyz.ksharma.krail.design.system.preview.PreviewComponent
import xyz.ksharma.krail.design.system.theme.KrailTheme

@Composable
fun TitleBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(modifier = Modifier.weight(1f)) {
            CompositionLocalProvider(
                LocalTextColor provides KrailTheme.colors.onBackground,
                LocalTextStyle provides KrailTheme.typography.headlineMedium,
            ) {
                title()
            }
        }
        actions?.let {
            Row(modifier = Modifier.padding(start = 16.dp)) {
                CompositionLocalProvider(
                    LocalContentColor provides KrailTheme.colors.onBackground,
                ) {
                    actions()
                }
            }
        }
    }
}

// region Previews

@PreviewComponent
@Composable
private fun TitleBarPreview() {
    KrailTheme {
        TitleBar(
            title = {
                Text(text = "Saved Trips Screen Title")
            },
            actions = {
                Image(
                    painter = painterResource(id = R.drawable.star_outline),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(LocalContentColor.current),
                )
            },
            modifier = Modifier.background(color = KrailTheme.colors.background),
        )
    }
}

@PreviewComponent
@Composable
private fun TitleBarPreviewNoActions() {
    KrailTheme {
        TitleBar(
            title = {
                Text(text = "Saved Trips Screen Title")
            },
            modifier = Modifier.background(color = KrailTheme.colors.background),
        )
    }
}

// endregion
