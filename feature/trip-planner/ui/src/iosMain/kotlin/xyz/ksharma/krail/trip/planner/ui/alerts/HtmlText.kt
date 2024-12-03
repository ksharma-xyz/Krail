package xyz.ksharma.krail.trip.planner.ui.alerts

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import xyz.ksharma.krail.taj.components.Text

@Composable
actual fun HtmlText(
    text: String,
    modifier: Modifier,
    onClick: () -> Unit,
    color: Color,
    urlColor: Color,
) {
    Text(text = replaceHtmlTags(text), modifier = Modifier.clickable { onClick() }, color = color)
}

// TODO - A workaround for iOS until https://issuetracker.google.com/issues/139326648 is fixed.
fun replaceHtmlTags(html: String): String {
   return runCatching {
        html
            .replace("</div>", "\n")
            .replace("</li>", "\n")
            .replace("</ul>", "\n")
            .replace("<li>", "")
            .replace("<ul>", "")
            .replace(Regex("<a href=\"[^\"]*\">"), " ")
            .replace("&nbsp;", " ")
            .replace(Regex("<[^>]*>"), "") // Remove all other HTML tags
            .replace(Regex("\n{3,}"), "\n\n")  // Replace multiple new lines with a single new line
            .trim()
    }.getOrElse {
        html // Return the original text if an exception occurs
    }
}
