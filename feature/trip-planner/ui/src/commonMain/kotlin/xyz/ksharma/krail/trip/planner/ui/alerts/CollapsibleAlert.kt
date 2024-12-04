package xyz.ksharma.krail.trip.planner.ui.alerts

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.taj.LocalThemeColor
import xyz.ksharma.krail.taj.components.Text
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.taj.theme.getForegroundColor
import xyz.ksharma.krail.taj.toAdaptiveSize
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.alerts.ServiceAlert

@Composable
fun CollapsibleAlert(
    serviceAlert: ServiceAlert,
    index: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    collapsed: Boolean = true,
) {
    val backgroundColor = KrailTheme.colors.alert.copy(alpha = 0.7f)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp),
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
            )
            .padding(vertical = 8.dp)
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp.toAdaptiveSize())
                    .clip(CircleShape)
                    .alignByBaseline(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "$index",
                    style = KrailTheme.typography.titleSmall,
                    color = getForegroundColor(backgroundColor),
                    modifier = Modifier
                )
            }

            Text(
                text = serviceAlert.heading,
                style = KrailTheme.typography.titleSmall,
                modifier = Modifier.padding(horizontal = 12.dp).alignByBaseline(),
                color = getForegroundColor(backgroundColor),
            )
        }

        if (collapsed.not()) {
            val isHtml by remember {
                mutableStateOf(
                    "<[a-z][\\s\\S]*>".toRegex().containsMatchIn(serviceAlert.message),
                )
            }
            if (isHtml) {
                HtmlText(
                    text = serviceAlert.message,
                    onClick = onClick,
                    color = getForegroundColor(backgroundColor),
                    urlColor = getForegroundColor(backgroundColor),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            } else {
                Text(
                    text = serviceAlert.message,
                    style = KrailTheme.typography.body,
                    color = getForegroundColor(backgroundColor),
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .padding(start = 12.dp + 24.dp.toAdaptiveSize(), bottom = 12.dp)
                    .background(
                        color = getForegroundColor(backgroundColor),
                        shape = RoundedCornerShape(50),
                    )
                    .padding(horizontal = 16.dp, vertical = 2.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Read More",
                    style = KrailTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = getForegroundColor(getForegroundColor(backgroundColor)),
                )
            }
        }
    }
}

// region Previews


//@Preview(fontScale = 2f)
@Composable
private fun PreviewCollapsibleAlertCollapsed() {
    KrailTheme {
        val color = remember { mutableStateOf(TransportMode.Ferry().colorCode) }
        CompositionLocalProvider(LocalThemeColor provides color) {
            CollapsibleAlert(
                serviceAlert = ServiceAlert(
                    heading = "Sample Alert",
                    message = "This is a sample alert message.",
                ),
                index = 1,
                onClick = {},
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}


@Composable
private fun PreviewCollapsibleAlertExpanded() {
    KrailTheme {
        val color = remember { mutableStateOf(TransportMode.Ferry().colorCode) }
        CompositionLocalProvider(LocalThemeColor provides color) {
            CollapsibleAlert(
                serviceAlert = ServiceAlert(
                    heading = "Sample Alert",
                    message = "This is a sample alert message.",
                ),
                collapsed = false,
                onClick = {},
                index = 1,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

// endregion
