package xyz.ksharma.krail.trip.planner.ui.datetimeselector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.taj.LocalThemeColor
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.taj.theme.getForegroundColor
import xyz.ksharma.krail.trip.planner.ui.components.hexToComposeColor
import xyz.ksharma.krail.trip.planner.ui.components.themeBackgroundColor


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