package xyz.ksharma.krail.trip.planner.ui.alerts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.components.TitleBar
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip.planner.ui.DefaultSystemBarColors
import xyz.ksharma.krail.trip.planner.ui.state.alerts.ServiceAlert
import xyz.ksharma.krail.trip.planner.ui.state.alerts.ServiceAlertPriority
import xyz.ksharma.krail.trip.planner.ui.timetable.ActionButton

@Composable
fun ServiceAlertScreen(
    serviceAlerts: ImmutableSet<ServiceAlert>,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {
    DefaultSystemBarColors()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = KrailTheme.colors.surface)
            .statusBarsPadding(),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            TitleBar(
                navAction = {
                    ActionButton(
                        onClick = onBackClick,
                        contentDescription = "Back",
                    ) {
                        Image(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(KrailTheme.colors.onSurface),
                            modifier = Modifier.size(24.dp),
                        )
                    }
                },
                title = { Text(text = "Service Alerts") },
            )
        }

        var expandedAlertId by rememberSaveable { mutableStateOf<Int?>(null) }

        LazyColumn(
            modifier = Modifier,
            contentPadding = PaddingValues(top = 20.dp, bottom = 104.dp),
        ) {
            itemsIndexed(
                items = serviceAlerts.toImmutableList(),
                key = { _, alert -> alert.hashCode() },
            ) { index, alert ->

                CollapsibleAlert(
                    serviceAlert = alert,
                    index = index + 1,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    collapsed = expandedAlertId != alert.hashCode(),
                    onClick = {
                        expandedAlertId = if (expandedAlertId == alert.hashCode()) {
                            null
                        } else {
                            alert.hashCode()
                        }
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewServiceAlertScreen() {
    KrailTheme {
        ServiceAlertScreen(
            serviceAlerts = persistentSetOf(
                ServiceAlert(
                    heading = "Service Alert 1",
                    message = "This is a service alert 1",
                    priority = ServiceAlertPriority.HIGH,
                ),
                ServiceAlert(
                    heading = "Service Alert 2",
                    message = "This is a service alert 2",
                    priority = ServiceAlertPriority.MEDIUM,
                ),
                ServiceAlert(
                    heading = "Service Alert 3",
                    message = "This is a service alert 3",
                    priority = ServiceAlertPriority.LOW,
                ),
            ),
        )
    }
}
