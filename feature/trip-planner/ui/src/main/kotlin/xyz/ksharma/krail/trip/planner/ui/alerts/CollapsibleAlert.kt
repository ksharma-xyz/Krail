package xyz.ksharma.krail.trip.planner.ui.alerts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip.planner.ui.R
import xyz.ksharma.krail.trip.planner.ui.components.themeBackgroundColor
import xyz.ksharma.krail.trip.planner.ui.state.alerts.Alert
import xyz.ksharma.krail.trip.planner.ui.state.alerts.ServiceAlertPriority

@Composable
fun CollapsibleAlert(
    alert: Alert,
    modifier: Modifier = Modifier,
    collapsed: Boolean = true,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = if (collapsed) KrailTheme.colors.surface else themeBackgroundColor(),
                shape = RoundedCornerShape(12.dp),
            ).padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(
                    if (collapsed) {
                        R.drawable.ic_arrow_right
                    } else {
                        R.drawable.ic_arrow_down
                    },
                ),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = KrailTheme.colors.label),
            )
            Text(alert.heading, style = KrailTheme.typography.titleSmall)
        }

        if (collapsed.not()) {
            Text(
                text = alert.message,
                style = KrailTheme.typography.body,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )
        }
    }
}

// region Previews

@PreviewLightDark
@Composable
private fun PreviewCollapsibleAlertCollapsed() {
    KrailTheme {
        CollapsibleAlert(
            alert = Alert(
                heading = "Sample Alert",
                message = "This is a sample alert message.",
                priority = ServiceAlertPriority.HIGH,
            ),
            modifier = Modifier.padding(16.dp),
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewCollapsibleAlertExpanded() {
    KrailTheme {
        CollapsibleAlert(
            alert = Alert(
                heading = "Sample Alert",
                message = "This is a sample alert message.",
                priority = ServiceAlertPriority.HIGH,
            ),
            collapsed = false,
            modifier = Modifier.padding(16.dp),
        )
    }
}

// endregion
