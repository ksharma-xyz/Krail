package xyz.ksharma.krail.trip.planner.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import krail.feature.trip_planner.ui.generated.resources.Res
import krail.feature.trip_planner.ui.generated.resources.ic_chevron_left
import krail.feature.trip_planner.ui.generated.resources.ic_chevron_right
import org.jetbrains.compose.resources.painterResource
import xyz.ksharma.krail.core.datetime.rememberCurrentDateTime
import xyz.ksharma.krail.taj.LocalThemeColor
import xyz.ksharma.krail.taj.components.Text
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.taj.theme.getForegroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeSelector(
    modifier: Modifier = Modifier,
    themeColor: Color = KrailTheme.colors.trainTheme,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(KrailTheme.colors.surface)
            .padding(vertical = 16.dp,)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            RadioButton(
                text = "Arrive",
                selected = true,
                backgroundColor = themeColor,
            )
            RadioButton(
                text = "Leave",
                backgroundColor = themeColor,

                )
            RadioButton(
                text = "Now",
                backgroundColor = themeColor,
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Select Date",
            style = KrailTheme.typography.title,
            color = KrailTheme.colors.onSurface,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                painter = painterResource(Res.drawable.ic_chevron_left),
                color = themeColor,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Text(
                text = "Wed, 10 Dec",
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Select Time",
                style = KrailTheme.typography.title,
                color = KrailTheme.colors.onSurface,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }

        val currentDateTime = rememberCurrentDateTime()

        val timePickerState = rememberTimePickerState(
            initialHour = currentDateTime.hour,
            initialMinute = currentDateTime.minute,
            is24Hour = false,
        )

        KrailTimePicker(timePickerState)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun KrailTimePicker(
    timePickerState: TimePickerState,
) {
    val themeColorHex by LocalThemeColor.current
    val themeColor = themeColorHex.hexToComposeColor()
    val themeContentColor = getForegroundColor(themeColor)

    TimePicker(
        state = timePickerState,
        colors = TimePickerColors(
            containerColor = KrailTheme.colors.surface, // Set container color to surface
            clockDialColor = KrailTheme.colors.surface, // Set clock dial color to surface
            selectorColor = themeColor, // Set selector color to themeColor
            periodSelectorBorderColor = themeColor, // Set period selector border color to themeColor
            clockDialSelectedContentColor = themeContentColor, // Set selected clock dial content color to themeColor
            clockDialUnselectedContentColor = KrailTheme.colors.onSurface.copy(alpha = 0.6f), // Set unselected clock dial content color to onSurface with reduced alpha
            periodSelectorSelectedContainerColor = themeColor, // Set selected period selector container color to themeColor
            periodSelectorUnselectedContainerColor = KrailTheme.colors.surface, // Set unselected period selector container color to surface
            periodSelectorSelectedContentColor = themeContentColor, // Set selected period selector content color to onSurface
            periodSelectorUnselectedContentColor = KrailTheme.colors.onSurface.copy(alpha = 0.6f), // Set unselected period selector content color to onSurface with reduced alpha
            timeSelectorSelectedContainerColor = themeColor, // Set selected time selector container color to themeColor
            timeSelectorUnselectedContainerColor = KrailTheme.colors.surface, // Set unselected time selector container color to surface
            timeSelectorSelectedContentColor = themeContentColor, // Set selected time selector content color to onSurface
            timeSelectorUnselectedContentColor = KrailTheme.colors.onSurface.copy(alpha = 0.6f) // Set unselected time selector content color to onSurface with reduced alpha
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun IconButton(
    painter: Painter,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .clickable { onClick() }, contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color),
            modifier = Modifier.size(24.dp),
        )
    }
}

@Composable
fun RadioButton(
    text: String,
    backgroundColor: Color,
    type: RadioButtonType = RadioButtonType.DEFAULT,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .height(
                when (type) {
                    RadioButtonType.SMALL -> 32.dp
                    RadioButtonType.DEFAULT -> 48.dp
                }
            )
            .clip(shape = RoundedCornerShape(8.dp))
            .background(
                color = if (selected) backgroundColor else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = if (!selected) 1.dp else 0.dp, // Apply border only when not selected
                color = backgroundColor, // Use backgroundColor for border color
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 4.dp, horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = KrailTheme.typography.title,
            color = if (selected) KrailTheme.colors.onSurface else backgroundColor, // themecontentcolor
        )
    }
}

enum class RadioButtonType {
    SMALL,
    DEFAULT,
}

// region preview

@Composable
private fun PreviewDateTimeSelector() {
    KrailTheme {
        Column(modifier = Modifier.background(color = KrailTheme.colors.surface)) {
            DateTimeSelector()
        }
    }
}

@Composable
private fun PreviewRadioButton() {
    KrailTheme {
        Box(modifier = Modifier.background(color = KrailTheme.colors.surface)) {
            RadioButton(
                text = "Hello",
                selected = true,
                backgroundColor = KrailTheme.colors.trainTheme,
                onClick = {})
        }
    }
}

@Composable
private fun PreviewRadioButtonUnselected() {
    KrailTheme {
        Box(modifier = Modifier.background(color = KrailTheme.colors.surface)) {
            RadioButton(
                text = "Hello",
                selected = false,
                backgroundColor = KrailTheme.colors.trainTheme,
                onClick = {})
        }
    }
}

// endregion
