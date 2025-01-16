package xyz.ksharma.krail.taj.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.taj.LocalContentAlpha
import xyz.ksharma.krail.taj.LocalContentColor
import xyz.ksharma.krail.taj.LocalOnContentColor
import xyz.ksharma.krail.taj.LocalTextStyle
import xyz.ksharma.krail.taj.LocalThemeColor
import xyz.ksharma.krail.taj.hexToComposeColor
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.taj.theme.getForegroundColor
import xyz.ksharma.krail.taj.tokens.ContentAlphaTokens.DisabledContentAlpha
import xyz.ksharma.krail.taj.tokens.ContentAlphaTokens.EnabledContentAlpha

/**
 * Button component for Taj
 *
 * @param label The text to be displayed on the button.
 * @param modifier The modifier to be applied to the button.
 * @param themeColor The background color of the button. If null, a default color is used.
 * @param enabled Whether the button is enabled or not. Defaults to true.
 * @param onClick The callback to be invoked when the button is clicked.
 */
@Composable
fun Button(
    label: String,
    modifier: Modifier = Modifier,
    themeColor: Color? = null, // TODO - concrete type for theme
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    val contentAlphaProvider =
        rememberSaveable(enabled) { if (enabled) EnabledContentAlpha else DisabledContentAlpha }

    val hexContentColor by LocalThemeColor.current
    val contentColor by remember(themeColor, hexContentColor) {
        mutableStateOf(themeColor ?: hexContentColor.hexToComposeColor())
    }
    val onContentColor by animateColorAsState(
        targetValue = getForegroundColor(contentColor),
        label = "buttonTextColor",
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
    )

    CompositionLocalProvider(
        LocalContentAlpha provides contentAlphaProvider,
        LocalTextStyle provides KrailTheme.typography.titleMedium,
        LocalContentColor provides contentColor,
        LocalOnContentColor provides onContentColor,
    ) {
        val contentAlpha = LocalContentAlpha.current

        Text(
            text = label,
            textAlign = TextAlign.Center,
            color = LocalOnContentColor.current.copy(alpha = contentAlpha),
            style = LocalTextStyle.current,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp) // TODO - tokens for padding
                .clip(RoundedCornerShape(50))
                .background(color = LocalContentColor.current.copy(alpha = contentAlpha))
                .clickable(
                    role = Role.Button,
                    interactionSource = remember { MutableInteractionSource() },
                    enabled = enabled,
                    indication = null,
                    onClick = onClick,
                )
                .padding(10.dp), // TODO - tokens for padding
        )
    }
}

/** TODO
 * Button
 *      Kind
 *      - Cta button - text only
 *      - Button - text with background color
 *
 *      Sizing
 *      - Compact - a small button with minimal padding horizontally
 *      - Default - a button with default padding horizontally
 *      - Large - a full width button.
 */
