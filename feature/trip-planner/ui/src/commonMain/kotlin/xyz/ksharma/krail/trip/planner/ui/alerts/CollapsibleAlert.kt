package xyz.ksharma.krail.trip.planner.ui.alerts

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.HtmlText
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.taj.LocalThemeColor
import xyz.ksharma.krail.taj.components.Text
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.taj.toAdaptiveSize
import xyz.ksharma.krail.trip.planner.ui.components.themeBackgroundColor
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.alerts.ServiceAlert
import xyz.ksharma.krail.trip.planner.ui.state.alerts.ServiceAlertPriority

@Composable
fun CollapsibleAlert(
    serviceAlert: ServiceAlert,
    index: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    collapsed: Boolean = true,
) {
    val indexBackgroundColor by animateColorAsState(
        targetValue = if (collapsed) themeBackgroundColor() else Color.Transparent,
        label = "indexBackgroundColor",
        animationSpec = tween(durationMillis = 300, easing = LinearEasing)
    )

    val parentBackgroundColor by animateColorAsState(
        targetValue = if (collapsed) KrailTheme.colors.surface else themeBackgroundColor(),
        label = "parentBackgroundColor",
        animationSpec = tween(durationMillis = 300, easing = LinearEasing)
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = parentBackgroundColor,
                shape = RoundedCornerShape(12.dp),
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
            )
            .padding(vertical = 8.dp)
            .padding(horizontal = 8.dp)
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
                    .background(
                        color = indexBackgroundColor,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = index.toString(),
                    style = KrailTheme.typography.titleSmall,
                )
            }

            Text(
                text = serviceAlert.heading,
                style = KrailTheme.typography.titleSmall,
                modifier = Modifier.padding(start = 12.dp),
            )
        }

        if (collapsed.not()) {
            val isHtml by remember {
                mutableStateOf(
                    "<[a-z][\\s\\S]*>".toRegex().containsMatchIn(serviceAlert.message),
                )
            }
            if (isHtml) {
                HtmlText(text = serviceAlert.message, onClick = onClick)
            } else {
                Text(
                    text = serviceAlert.message,
                    style = KrailTheme.typography.body,
                    modifier = Modifier.padding(vertical = 8.dp),
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
                    priority = ServiceAlertPriority.HIGH,
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
                    priority = ServiceAlertPriority.HIGH,
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
