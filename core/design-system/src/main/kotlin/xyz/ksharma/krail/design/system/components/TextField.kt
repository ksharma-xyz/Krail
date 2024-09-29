package xyz.ksharma.krail.design.system.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import xyz.ksharma.krail.design.system.components.foundation.Text
import xyz.ksharma.krail.design.system.theme.KrailTheme

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
    val textFieldState = rememberTextFieldState()

    CompositionLocalProvider(
        LocalTextColor provides KrailTheme.colors.onSecondaryContainer,
        LocalTextStyle provides KrailTheme.typography.titleLarge,
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
            cursorBrush = SolidColor(KrailTheme.colors.onSecondaryContainer),
            decorator = { innerTextField ->
                Row(
                    modifier = Modifier
                        .background(
                            shape = RoundedCornerShape(TextFieldHeight.div(2)),
                            color = KrailTheme.colors.secondaryContainer
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (textFieldState.text.isEmpty()) {
                        Text(
                            text = placeholder.orEmpty(),
                            style = LocalTextStyle.current,
                            color = LocalTextColor.current,
                            maxLines = 1,
                        )
                    } else {

                        // add leading icon here
                        innerTextField()

                        // add trailing icon here / Clear - todo
                    }
                }
            }
        )
    }
}

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
