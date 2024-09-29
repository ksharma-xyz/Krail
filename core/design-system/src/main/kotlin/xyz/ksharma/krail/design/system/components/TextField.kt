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
import xyz.ksharma.krail.design.system.LocalTextColor
import xyz.ksharma.krail.design.system.LocalTextStyle
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.design.system.tokens.TextFieldTokens.PlaceholderOpacity

val TextFieldHeight = 48.dp

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    enabled: Boolean = true,
    textStyle: TextStyle? = null,
    readOnly: Boolean = false,
    imeAction: ImeAction = ImeAction.Default,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val textFieldState = rememberTextFieldState()

    val textSelectionColors = TextSelectionColors(
        handleColor = KrailTheme.colors.tertiary,
        backgroundColor = KrailTheme.colors.tertiary.copy(alpha = 0.4f)
    )

    CompositionLocalProvider(
        LocalTextColor provides KrailTheme.colors.onSecondaryContainer,
        LocalTextStyle provides KrailTheme.typography.titleLarge,
        LocalTextSelectionColors provides textSelectionColors,
    ) {
        BasicTextField(
            state = textFieldState,
            enabled = enabled,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxWidth()
                .height(TextFieldHeight),
            textStyle = textStyle ?: LocalTextStyle.current,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Text,
                imeAction = imeAction,
                hintLocales = LocaleList.current
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
                        .padding(horizontal = 12.dp, vertical = 4.dp),
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
        style = LocalTextStyle.current,
        color = LocalTextColor.current.copy(alpha = PlaceholderOpacity),
        maxLines = 1,
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
private fun TextFieldDisabledPreview() {
    KrailTheme {
        TextField(enabled = false, placeholder = "Station")
    }
}

// endregion

/**
 * TODO -
 * 1. Display cursor when TextField is focused.
 * 2. Add support for leading and trailing icons.
 * 3. Change Text Color based on enabled state.
 * 4. Support Disabled State colors.
 */
