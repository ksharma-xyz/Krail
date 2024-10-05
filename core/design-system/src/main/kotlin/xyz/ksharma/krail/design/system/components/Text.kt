package xyz.ksharma.krail.design.system.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import xyz.ksharma.krail.design.system.LocalContentAlpha
import xyz.ksharma.krail.design.system.LocalTextColor
import xyz.ksharma.krail.design.system.LocalTextStyle
import xyz.ksharma.krail.design.system.preview.ComponentPreviews
import xyz.ksharma.krail.design.system.theme.KrailTheme

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color? = null,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = if (maxLines == Int.MAX_VALUE) TextOverflow.Clip else TextOverflow.Ellipsis,
) {
    val contentAlpha = LocalContentAlpha.current
    CompositionLocalProvider(
        LocalTextColor provides KrailTheme.colors.onBackground, // default color for text
        LocalTextStyle provides KrailTheme.typography.body,  // default style for text
    ) {
        BasicText(
            text = text,
            style = style.merge(
                color = color ?: LocalTextColor.current.copy(alpha = contentAlpha),
                textAlign = textAlign,
            ),
            maxLines = maxLines,
            overflow = overflow,
            modifier = modifier,
        )
    }
}

// region Previews

@ComponentPreviews
@Composable
private fun TextPreview() {
    KrailTheme {
        Column(modifier = Modifier.background(color = KrailTheme.colors.background)) {
            Text(text = "Typography")
            Text(text = "DisplayLarge", style = KrailTheme.typography.displayLarge)
            Text(text = "displayMedium", style = KrailTheme.typography.displayMedium)
            Text(text = "displaySmall", style = KrailTheme.typography.displaySmall)
        }
    }
}

@ComponentPreviews
@Composable
private fun TextWithColorPreview() {
    KrailTheme {
        Column(modifier = Modifier.background(color = KrailTheme.colors.background)) {
            Text(text = "Typography", color = KrailTheme.colors.error)
        }
    }
}

// endregion
