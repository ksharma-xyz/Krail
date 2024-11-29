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
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.core.datetime.rememberCurrentDateTime
import xyz.ksharma.krail.taj.LocalThemeColor
import xyz.ksharma.krail.taj.components.Text
import xyz.ksharma.krail.taj.components.TitleBar
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.trip.planner.ui.components.hexToComposeColor
import xyz.ksharma.krail.trip.planner.ui.timetable.ActionButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeSelectorScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onDateTimeSelected: () -> Unit = {},
) {
    val themeColorHex by LocalThemeColor.current
    val themeColor = remember(themeColorHex) {
        themeColorHex.hexToComposeColor()
    }
    val currentDateTime = rememberCurrentDateTime()
    var journeyTimeOption by rememberSaveable { mutableStateOf(JourneyTimeOptions.LEAVE) }
    val timePickerState = rememberTimePickerState(
        initialHour = currentDateTime.hour,
        initialMinute = currentDateTime.minute,
        is24Hour = false,
    )

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
                JourneyTimeOptionsGroup(
                    selectedOption = journeyTimeOption,
                    themeColor = themeColor,
                    onOptionSelected = { journeyTimeOption = it },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)
                )
            }

            item {
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
                SelectedDateTimeRow(
                    dateTimeText = "Today, 12th July, 10:30 AM",
                    onResetClick = {},
                )
            }
        }
    }
}

@Composable
private fun SelectedDateTimeRow(
    dateTimeText: String,
    modifier: Modifier = Modifier,
    onResetClick: () -> Unit = {},
) {
    Row(
        modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("Reset", modifier = Modifier.clickable { onResetClick() })
        Text(dateTimeText)
    }
}
