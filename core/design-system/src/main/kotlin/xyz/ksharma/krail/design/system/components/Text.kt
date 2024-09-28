package xyz.ksharma.krail.design.system.components

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import xyz.ksharma.krail.design.system.theme.KrailTheme

val LocalTextStyle = compositionLocalOf { TextStyle.Default }
val LocalTextColor = compositionLocalOf { Color.Unspecified }

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
) {
    CompositionLocalProvider(
        LocalTextColor provides KrailTheme.colors.onBackground,
        LocalTextStyle provides KrailTheme.typography.body,
    ) {
        BasicText(
            text = text,
            style = style.merge(
                color = LocalTextColor.current,
                textAlign = textAlign,

                ),
            maxLines = maxLines,
            modifier = modifier,
        )
    }
}

@PreviewLightDark
@Composable
private fun TextPreview() {
    KrailTheme {
        Text(text = "Hello World!")
        Text(text = "Hello World!", style = KrailTheme.typography.displayLarge)
        Text(text = "Hello World!", style = KrailTheme.typography.displayMedium)
        Text(text = "Hello World!", style = KrailTheme.typography.displaySmall)
    }
}
