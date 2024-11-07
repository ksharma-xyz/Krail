package xyz.ksharma.krail.trip.planner.ui.alerts

import android.text.Spanned
import android.text.SpannedString
import android.text.style.BulletSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.core.text.HtmlCompat

fun String.toAnnotatedString(urlColor: Color): AnnotatedString {
    val isHtml = "<[a-z][\\s\\S]*>".toRegex().containsMatchIn(this)
    val spanned: Spanned = if (isHtml) {
        HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
    } else {
        SpannedString(this)
    }

    return buildAnnotatedString {
        var currentPosition = 0

        spanned.getSpans(0, spanned.length, Any::class.java).forEach { span ->
            val start = spanned.getSpanStart(span)
            val end = spanned.getSpanEnd(span)

            if (currentPosition < start) {
                append(spanned.subSequence(currentPosition, start).toString())
                currentPosition = start
            }

            when (span) {
                is BulletSpan -> {
                    append("• ")
                    append(spanned.subSequence(start, end).toString())
                }
                is StyleSpan -> {
                    when (span.style) {
                        android.graphics.Typeface.BOLD -> {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(spanned.subSequence(start, end).toString())
                            }
                        }
                        android.graphics.Typeface.ITALIC -> {
                            withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                                append(spanned.subSequence(start, end).toString())
                            }
                        }
                    }
                }
                is URLSpan -> {
                    pushStringAnnotation(tag = "URL", annotation = span.url)
                    withStyle(style = SpanStyle(color = urlColor, textDecoration = TextDecoration.Underline)) {
                        append(spanned.subSequence(start, end).toString())
                    }
                    pop()
                }
                else -> {
                    append(spanned.subSequence(start, end).toString())
                }
            }
            currentPosition = end
        }

        if (currentPosition < spanned.length) {
            append(spanned.subSequence(currentPosition, spanned.length).toString())
        }
    }
}
