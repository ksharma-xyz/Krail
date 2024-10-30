package xyz.ksharma.krail.design.system.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import xyz.ksharma.krail.design.system.LocalContentColor
import xyz.ksharma.krail.design.system.LocalOnContentColor
import xyz.ksharma.krail.design.system.R
import xyz.ksharma.krail.design.system.preview.PreviewComponent
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.design.system.tokens.ButtonTokens.RoundButtonSize

/**
 * A round icon button with customizable content and colors.
 *
 * @param modifier Modifier to be applied to the button.
 * @param color Background color of the button. Defaults to the theme's secondary container color.
 * @param onClickLabel Semantic / accessibility label for the [onClick] action.
 * @param content Composable content to be displayed inside the button.
 * @param onClick Lambda to be invoked when the button is clicked.
 */
@Composable
fun RoundIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color? = null,
    onClickLabel: String? = null,
    content: @Composable () -> Unit = {},
) {
    CompositionLocalProvider(
        LocalContentColor provides KrailTheme.colors.surface,
        LocalOnContentColor provides KrailTheme.colors.onSurface,
    ) {
        Box(
            modifier = modifier
                .size(RoundButtonSize) // TODO - token "SearchButtonHeight"
                .clip(CircleShape)
                .background(color = color ?: LocalContentColor.current)
                .clickable(
                    role = Role.Button,
                    onClickLabel = onClickLabel,
                ) { onClick() },
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}

// region Previews

@PreviewComponent
@Composable
private fun RoundIconButtonPreview() {
    KrailTheme {
        RoundIconButton(onClick = {}) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.star),
                contentDescription = null,
                colorFilter = ColorFilter.tint(LocalOnContentColor.current),
            )
        }
    }
}

// endregion
