package xyz.ksharma.krail.trip.planner.ui.alerts

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.ksharma.krail.taj.components.Text

@Composable
actual fun HtmlText(text: String, modifier: Modifier, onClick: () -> Unit) {
    Text(text = replaceHtmlTags(text), modifier = Modifier.clickable { onClick() })
}

// TODO - A workaround for iOS until https://issuetracker.google.com/issues/139326648 is fixed.
private fun replaceHtmlTags(html: String): String {
    return html
        .replace("</div>", "\n")
        .replace("</li>", "\n")
        .replace("<ul>", "\n")
        .replace(Regex("<a href=\"[^\"]*\">"), " ")
        .replace("&nbsp;", " ")
        .replace(Regex("<[^>]*>"), "") // Remove all other HTML tags
        .replace(Regex("\n{3,}"), "\n\n")  // Replace multiple new lines with a single new line
}
