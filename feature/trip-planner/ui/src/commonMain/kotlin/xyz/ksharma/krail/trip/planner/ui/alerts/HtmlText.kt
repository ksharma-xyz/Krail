package xyz.ksharma.krail.trip.planner.ui.alerts

import android.graphics.Typeface
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import xyz.ksharma.krail.design.system.theme.KrailTheme

/**
 * Reference - https://developer.android.com/codelabs/jetpack-compose-migration#8
 */
@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    // Remembers the HTML formatted description. Re-executes on a new description
    val htmlDescription = remember(html) {
        HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    val textColor = KrailTheme.colors.label.toArgb()
    val textStyle = KrailTheme.typography.bodyLarge
    val resolver: FontFamily.Resolver = LocalFontFamilyResolver.current
    val urlColor = KrailTheme.colors.alert.toArgb()

    val textTypeface: Typeface = remember(resolver, textStyle) {
        resolver.resolve(
            fontFamily = textStyle.fontFamily,
            fontWeight = textStyle.fontWeight ?: FontWeight.Normal,
            fontStyle = textStyle.fontStyle ?: FontStyle.Normal,
            fontSynthesis = textStyle.fontSynthesis ?: FontSynthesis.All,
        )
    }.value as Typeface

    // Displays the TextView on the screen and updates with the HTML description when inflated
    // Updates to htmlDescription will make AndroidView recompose and update the text
    AndroidView(
        factory = { context ->
            TextView(context).apply {
                movementMethod = LinkMovementMethod.getInstance()
                setTextColor(textColor)
                setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, textStyle.fontSize.value)
                typeface = textTypeface
                setLinkTextColor(urlColor)
                setOnClickListener {
                    onClick()
                }
            }
        },
        update = {
            it.text = htmlDescription
        },
        modifier = modifier,
    )
}
