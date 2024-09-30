package xyz.ksharma.krail.design.system.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.LocalContentAlpha
import xyz.ksharma.krail.design.system.LocalTextColor
import xyz.ksharma.krail.design.system.LocalTextStyle
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.design.system.tokens.TextFieldTokens
import xyz.ksharma.krail.design.system.tokens.TextFieldTokens.PlaceholderOpacity
import xyz.ksharma.krail.design.system.tokens.TextFieldTokens.TextFieldHeight
import xyz.ksharma.krail.design.system.tokens.TextFieldTokens.TextSelectionBackgroundOpacity

/**
 * Important documentation links:
 * https://developer.android.com/jetpack/androidx/releases/compose-foundation#1.7.0
 */
@Composable
fun TextField(
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    initialText: String? = null,
    enabled: Boolean = true,
    textStyle: TextStyle? = null,
    readOnly: Boolean = false,
    imeAction: ImeAction = ImeAction.Default,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val contentAlpha = if (enabled) 1f else TextFieldTokens.DisabledLabelOpacity

    val textFieldState = rememberTextFieldState(initialText.orEmpty())
    val textSelectionColors = TextSelectionColors(
        handleColor = KrailTheme.colors.tertiary,
        backgroundColor = KrailTheme.colors.tertiary.copy(alpha = TextSelectionBackgroundOpacity)
    )

    CompositionLocalProvider(
        LocalTextColor provides KrailTheme.colors.onSecondaryContainer,
        LocalTextStyle provides KrailTheme.typography.titleLarge,
        LocalTextSelectionColors provides textSelectionColors,
        LocalContentAlpha provides contentAlpha,
    ) {
        BasicTextField(
            state = textFieldState,
            enabled = enabled,
            modifier = modifier
                .fillMaxWidth()
                .height(TextFieldHeight),
            // This will change the colors of the innerTextField() composable.
            textStyle = textStyle
                ?: LocalTextStyle.current.copy(
                    color = LocalTextColor.current.copy(alpha = contentAlpha)
                ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Text,
                imeAction = imeAction,
                hintLocales = LocaleList.current, // todo - required?
            ),
            lineLimits = TextFieldLineLimits.SingleLine,
            readOnly = readOnly,
            interactionSource = interactionSource,
            cursorBrush = SolidColor(KrailTheme.colors.onSecondaryContainer),
            decorator = { innerTextField ->
                Row(
                    modifier = Modifier
                        .background(
                            shape = RoundedCornerShape(TextFieldHeight.div(2)),
                            color = KrailTheme.colors.secondaryContainer
                        )
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (textFieldState.text.isEmpty() && isFocused) {
                        /* Using a Box ensures that cursor and placeholder are displayed on top of
                     each other, so the cursor is always displayed at the start if the TextField.
                     */
                        Box {
                            innerTextField() // To display cursor
                            TextFieldPlaceholder(placeholder = placeholder)
                        }
                    } else if (textFieldState.text.isEmpty()) {
                        TextFieldPlaceholder(placeholder = placeholder)
                    } else {
                        // add leading icon here - todo
                        innerTextField()
                        // add trailing icon here / Clear - todo
                    }
                }
            }
        )
    }
}

@Composable
private fun TextFieldPlaceholder(placeholder: String? = null) {
    Text(
        text = placeholder.orEmpty(),
        maxLines = 1,
        color = LocalTextColor.current.copy(alpha = PlaceholderOpacity),
    )
}

// region Previews

@PreviewLightDark
@Composable
private fun TextFieldEnabledPreview() {
    KrailTheme {
        TextField(placeholder = "Station")
    }
}

@PreviewLightDark
@Composable
private fun TextFieldEnabledPlaceholderPreview() {
    KrailTheme {
        TextField(placeholder = "Placeholder")
    }
}

@PreviewLightDark
@Composable
private fun TextFieldDisabledPreview() {
    KrailTheme {
        TextField(enabled = false, initialText = "Disabled TextField")
    }
}

@PreviewLightDark
@Composable
private fun TextFieldDisabledPlaceholderPreview() {
    KrailTheme {
            TextField(enabled = false, placeholder = "Disabled Placeholder")
    }
}

// endregion
