package xyz.ksharma.krail.design.system.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textStyle: TextStyle? = null,
    readOnly: Boolean = false,
    imeAction: ImeAction = ImeAction.Default,
) {
    val textFieldState by remember {
        mutableStateOf(TextFieldState())
    }

    CompositionLocalProvider(
        LocalTextColor provides KrailTheme.colors.onSecondaryContainer,
        LocalTextStyle provides KrailTheme.typography.titleLarge,
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color = KrailTheme.colors.secondaryContainer)
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {

            BasicTextField(
                state = textFieldState,
                enabled = enabled,
                modifier = Modifier.fillMaxWidth(),
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
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun TextFieldEnabledPreview() {
    KrailTheme {
        TextField()
    }
}

@PreviewLightDark
@Composable
private fun TextFieldDisabledPreview() {
    KrailTheme {
        TextField(enabled = false)
    }
}
