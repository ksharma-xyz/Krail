package xyz.ksharma.krail.trip_planner.ui.searchstop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip_planner.ui.state.searchstop.SearchStopState
import xyz.ksharma.krail.trip_planner.ui.state.searchstop.SearchStopUiEvent

@Composable
fun SearchStopScreen(
    searchStopState: SearchStopState,
    modifier: Modifier = Modifier,
    onEvent: (SearchStopUiEvent) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = KrailTheme.colors.secondaryContainer)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Text("SearchStopScreen", style = KrailTheme.typography.bodyLarge)
    }
}
