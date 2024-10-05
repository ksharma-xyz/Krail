package xyz.ksharma.krail.design.system.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.LocalContentAlpha
import xyz.ksharma.krail.design.system.LocalTextColor
import xyz.ksharma.krail.design.system.LocalTextStyle
import xyz.ksharma.krail.design.system.preview.ComponentPreviews
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.design.system.tokens.TextFieldTokens
import xyz.ksharma.krail.design.system.tokens.TextFieldTokens.TextFieldHeight

/**
 * A button that looks like a text field.
 *
 * @param modifier The modifier to apply to this component.
 * @param enabled Whether the button is enabled.
 * @param content The content of the button.
 *
 * TODO - how to ensure modifiers for TextField and TextFieldButton are consistent?
 */
@Composable
fun TextFieldButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val contentAlpha = if (enabled) 1f else TextFieldTokens.DisabledLabelOpacity

    CompositionLocalProvider(
        LocalTextColor provides KrailTheme.colors.onSecondaryContainer,
        LocalTextStyle provides KrailTheme.typography.titleLarge,
        LocalContentAlpha provides contentAlpha,
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(TextFieldHeight)
                .clip(RoundedCornerShape(TextFieldHeight.div(2)))
                .background(color = KrailTheme.colors.secondaryContainer)
                .clickable(role = Role.Button, onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 4.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            content()
        }
    }
}

// region Previews

@ComponentPreviews
@Composable
private fun TextFieldButtonPreview() {
    KrailTheme {
        TextFieldButton(onClick = {}) {
            Text(text = "Search")
        }
    }
}

// endregion
