package xyz.ksharma.krail.design.system.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.LocalContentColor
import xyz.ksharma.krail.design.system.R
import xyz.ksharma.krail.design.system.theme.KrailTheme

/**
 * FAB is a floating action button that represents the primary action of a screen.
 *
 * @param containerColor The background color of the FAB.
 * @param contentColor The color of the content (e.g., icon) inside the FAB.
 * @param onClick The callback to be invoked when the FAB is clicked.
 * @param modifier The modifier to be applied to the FAB.
 * @param content The composable content to be displayed inside the FAB.
 */
@Composable
fun Fab(
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(24.dp)
            .clickable(role = Role.Button, onClick = onClick),
        contentAlignment = Alignment.BottomEnd,
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(color = containerColor, shape = CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            CompositionLocalProvider(LocalContentColor provides contentColor) {
                content()
            }
        }
    }
}

// region Preview

@PreviewLightDark
@Composable
private fun PreviewFab() {
    KrailTheme {
        Fab(
            containerColor = KrailTheme.colors.secondaryContainer,
            contentColor = KrailTheme.colors.secondary,
            onClick = {},
        ) {
            Image(
                painter = painterResource(R.drawable.star_outline),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = LocalContentColor.current),
                modifier = Modifier.size(32.dp),
            )
        }
    }
}

// endregion
