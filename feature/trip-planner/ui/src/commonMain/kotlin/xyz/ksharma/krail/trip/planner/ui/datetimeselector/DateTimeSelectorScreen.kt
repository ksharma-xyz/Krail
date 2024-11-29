package xyz.ksharma.krail.trip.planner.ui.datetimeselector

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import krail.feature.trip_planner.ui.generated.resources.Res
import krail.feature.trip_planner.ui.generated.resources.ic_chevron_left
import krail.feature.trip_planner.ui.generated.resources.ic_chevron_right
import org.jetbrains.compose.resources.painterResource
import xyz.ksharma.krail.core.datetime.rememberCurrentDateTime
import xyz.ksharma.krail.taj.LocalThemeColor
import xyz.ksharma.krail.taj.components.Text
import xyz.ksharma.krail.taj.components.TitleBar
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.taj.theme.getForegroundColor
import xyz.ksharma.krail.trip.planner.ui.components.IconButton
import xyz.ksharma.krail.trip.planner.ui.components.RadioButton
import xyz.ksharma.krail.trip.planner.ui.components.hexToComposeColor
import xyz.ksharma.krail.trip.planner.ui.components.themeBackgroundColor
import xyz.ksharma.krail.trip.planner.ui.timetable.ActionButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeSelectorScreen(modifier: Modifier = Modifier, onBackClick: () -> Unit = {}) {
    val themeColorHex by LocalThemeColor.current
    val themeColor = remember(themeColorHex) {
        themeColorHex.hexToComposeColor()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = KrailTheme.colors.surface),
    ) {
        TitleBar(
            title = { Text(text = "Plan your trip") },
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
        )

        LazyColumn(contentPadding = PaddingValues(vertical = 16.dp)) {
            item {
                JourneyTimeOptionsRow(
                    themeColor = themeColor,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)
                )
            }

            item {
                val currentDateTime = rememberCurrentDateTime()
                val timePickerState = rememberTimePickerState(
                    initialHour = currentDateTime.hour,
                    initialMinute = currentDateTime.minute,
                    is24Hour = false,
                )
                TimeSelection(
                    timePickerState = timePickerState,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)
                        .align(Alignment.CenterHorizontally),
                )
            }

            item {
                DateSelection(
                    themeColor = themeColor,
                    date = "Today, 12th July",
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }

            item {
                SelectedDateTimeRow()
            }
        }
    }
}

@Composable
private fun SelectedDateTimeRow(modifier: Modifier = Modifier, resetClick: () -> Unit = {}) {
    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        Text("Reset", modifier = Modifier.clickable { resetClick() })
        Text("Leaving at 12:00 PM")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSelection(
    timePickerState: TimePickerState,
    modifier: Modifier = Modifier,
) {
    val themeColorHex by LocalThemeColor.current
    val themeColor = themeColorHex.hexToComposeColor()
    val themeContentColor = getForegroundColor(themeColor)

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        val density = LocalDensity.current
        LaunchedEffect(density) {
            println("Density: $density")
        }
        CompositionLocalProvider(
            LocalDensity provides Density(
                (density.density - 0.6f).coerceIn(1.5f, 3f),
                fontScale = density.fontScale
            ),
        ) {
            TimePicker(
                state = timePickerState,
                colors = TimePickerColors(
                    containerColor = themeBackgroundColor(),
                    clockDialColor = themeBackgroundColor(),
                    selectorColor = themeColor,
                    periodSelectorBorderColor = themeColor,
                    clockDialSelectedContentColor = themeContentColor,
                    clockDialUnselectedContentColor = KrailTheme.colors.onSurface.copy(alpha = 0.8f),
                    periodSelectorSelectedContainerColor = themeColor,
                    periodSelectorUnselectedContainerColor = KrailTheme.colors.surface,
                    periodSelectorSelectedContentColor = themeContentColor,
                    periodSelectorUnselectedContentColor = KrailTheme.colors.onSurface.copy(alpha = 0.6f),
                    timeSelectorSelectedContainerColor = themeColor,
                    timeSelectorUnselectedContainerColor = KrailTheme.colors.surface,
                    timeSelectorSelectedContentColor = themeContentColor,
                    timeSelectorUnselectedContentColor = KrailTheme.colors.onSurface.copy(alpha = 0.8f)
                ),
                layoutType = TimePickerLayoutType.Vertical,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun DateSelection(
    themeColor: Color,
    date: String,
    modifier: Modifier = Modifier,
    onDateSelected: () -> Unit = {},
) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            painter = painterResource(Res.drawable.ic_chevron_left),
            color = themeColor,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Text(
            text = date,
            style = KrailTheme.typography.bodyLarge,
            color = KrailTheme.colors.onSurface,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
        )
        IconButton(
            painter = painterResource(Res.drawable.ic_chevron_right),
            color = themeColor,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
private fun JourneyTimeOptionsRow(themeColor: Color, modifier: Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        RadioButton(
            text = "Leave",
            themeColor = themeColor,
            selected = true,
            modifier = Modifier.padding(start = 16.dp),
        )
        RadioButton(
            text = "Arrive",
            selected = false,
            themeColor = themeColor,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
