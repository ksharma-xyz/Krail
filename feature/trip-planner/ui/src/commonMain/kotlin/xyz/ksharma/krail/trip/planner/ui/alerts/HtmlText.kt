package xyz.ksharma.krail.trip.planner.ui.alerts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
expect fun HtmlText(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    color: Color,
    urlColor: Color,
)
