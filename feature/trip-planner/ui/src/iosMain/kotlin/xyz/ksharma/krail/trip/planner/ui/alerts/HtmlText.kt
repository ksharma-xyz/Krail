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
    Text(text = text, modifier = Modifier.clickable { onClick() }, color = color)
}

// TODO - A workaround for iOS until https://issuetracker.google.com/issues/139326648 is fixed.
fun replaceHtmlTags(html: String): String {
    return html // Return the original text if an exception occurs
}
