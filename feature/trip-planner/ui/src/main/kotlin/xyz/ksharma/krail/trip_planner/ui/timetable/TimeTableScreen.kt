package xyz.ksharma.krail.trip_planner.ui.timetable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableState
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableUiEvent

@Composable
fun TimeTableScreen(
    timeTableState: TimeTableState,
    modifier: Modifier = Modifier,
    onEvent: (TimeTableUiEvent) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = KrailTheme.colors.secondaryContainer)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Text("TimeTableScreen", style = KrailTheme.typography.bodyLarge)
    }
}
