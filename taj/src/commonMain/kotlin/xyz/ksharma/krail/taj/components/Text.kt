package xyz.ksharma.krail.taj.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import xyz.ksharma.krail.taj.LocalContentAlpha
import xyz.ksharma.krail.taj.LocalTextColor
import xyz.ksharma.krail.taj.LocalTextStyle
import xyz.ksharma.krail.taj.theme.KrailTheme

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color? = null,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = if (maxLines == Int.MAX_VALUE) TextOverflow.Clip else TextOverflow.Ellipsis,
    fontFamily: FontFamily? = null,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
) {
    Text(
        text = AnnotatedString(text),
        modifier = modifier,
        style = style,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow,
        fontFamily = fontFamily,
        onTextLayout = onTextLayout,
    )
}

@Composable
fun Text(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color? = null,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = if (maxLines == Int.MAX_VALUE) TextOverflow.Clip else TextOverflow.Ellipsis,
    fontFamily: FontFamily? = null,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
) {
    val contentAlpha = LocalContentAlpha.current
    CompositionLocalProvider(
        LocalTextColor provides KrailTheme.colors.onSurface, // default color for text
        LocalTextStyle provides KrailTheme.typography.body, // default style for text
    ) {
        BasicText(
            text = text,
            style = style.merge(
                color = color?.copy(alpha = contentAlpha)
                    ?: LocalTextColor.current.copy(alpha = contentAlpha),
                textAlign = textAlign,
                fontFamily = fontFamily,
            ),
            maxLines = maxLines,
            overflow = overflow,
            modifier = modifier,
            onTextLayout = onTextLayout,
        )
    }
}

// region Previews

@Composable
private fun TextPreview() {
    KrailTheme {
        Column(modifier = Modifier.background(color = KrailTheme.colors.surface)) {
            Text(text = "Typography")
            Text(text = "DisplayLarge", style = KrailTheme.typography.displayLarge)
            Text(text = "displayMedium", style = KrailTheme.typography.displayMedium)
            Text(text = "displaySmall", style = KrailTheme.typography.displaySmall)
        }
    }
}

@Composable
private fun TextWithColorPreview() {
    KrailTheme {
        Column(modifier = Modifier.background(color = KrailTheme.colors.surface)) {
            Text(text = "Typography", color = KrailTheme.colors.error)
        }
    }
}

// endregion
