package xyz.ksharma.krail.trip.planner.ui.datetimeselector

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import xyz.ksharma.krail.core.datetime.decrementDateByOneDay
import xyz.ksharma.krail.core.datetime.formatDate
import xyz.ksharma.krail.core.datetime.formatTime
import xyz.ksharma.krail.core.datetime.incrementDateByOneDay
import xyz.ksharma.krail.core.datetime.rememberCurrentDateTime
import xyz.ksharma.krail.taj.LocalThemeColor
import xyz.ksharma.krail.taj.components.Text
import xyz.ksharma.krail.taj.components.TitleBar
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.trip.planner.ui.components.hexToComposeColor
import xyz.ksharma.krail.trip.planner.ui.components.themeBackgroundColor
import xyz.ksharma.krail.trip.planner.ui.components.themeContentColor
import xyz.ksharma.krail.trip.planner.ui.timetable.ActionButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeSelectorScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onDateTimeSelected: () -> Unit = {},
) {
    // Colors
    val themeColorHex by LocalThemeColor.current
    val themeColor = remember(themeColorHex) { themeColorHex.hexToComposeColor() }

    // Journey Time Options
    var journeyTimeOption by rememberSaveable { mutableStateOf(JourneyTimeOptions.LEAVE) }

    // Time Selection
    val currentDateTime = rememberCurrentDateTime()
    val timePickerState = rememberTimePickerState(
        initialHour = currentDateTime.hour,
        initialMinute = currentDateTime.minute,
        is24Hour = false,
    )

    // Date selection
    val today =
        remember { Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date }
    val maxDate = remember { today.plus(7, DateTimeUnit.DAY) }
    var selectedDate by remember { mutableStateOf(today) }

    Column(
        modifier = modifier.fillMaxSize().background(color = KrailTheme.colors.surface),
    ) {
        TitleBar(title = { Text(text = "Plan your trip") }, navAction = {
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
        }, actions = {
            Text(
                text = "Reset",
                style = KrailTheme.typography.titleSmall.copy(fontWeight = FontWeight.Normal),
                modifier = Modifier
                    .padding(10.dp)
                    .background(color = themeBackgroundColor(), shape = RoundedCornerShape(50))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .clickable(
                        role = Role.Button,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        val now: LocalDateTime =
                            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                        selectedDate = now.date
                        timePickerState.hour = now.time.hour
                        timePickerState.minute = now.time.minute
                    })
        })

        LazyColumn(contentPadding = PaddingValues(vertical = 16.dp)) {
            item {
                JourneyTimeOptionsGroup(
                    selectedOption = journeyTimeOption,
                    themeColor = themeColor,
                    onOptionSelected = { journeyTimeOption = it },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }

            item {
                TimeSelection(
                    timePickerState = timePickerState,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                        .align(Alignment.CenterHorizontally),
                )
            }

            item {
                DateSelection(
                    themeColor = themeColor,
                    date = "${formatDate(selectedDate)}, ${
                        formatTime(
                            hour = timePickerState.hour,
                            minute = timePickerState.minute,
                        )
                    }",
                    onNextClicked = {
                        if (selectedDate < maxDate) {
                            selectedDate = incrementDateByOneDay(selectedDate)
                        }
                    },
                    onPreviousClicked = {
                        if (selectedDate > today) {
                            selectedDate = decrementDateByOneDay(selectedDate)
                        }
                    },
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }

            item {
                Text(
                    text = "Done",
                    textAlign = TextAlign.Center,
                    color = themeContentColor(),
                    style = KrailTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                        .clip(RoundedCornerShape(50))
                        .background(color = themeColor).clickable(
                            role = Role.Button,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) {
                            onDateTimeSelected()
                        }.padding(vertical = 10.dp),
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