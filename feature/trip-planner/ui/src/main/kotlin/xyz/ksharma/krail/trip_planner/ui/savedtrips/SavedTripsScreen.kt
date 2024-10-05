package xyz.ksharma.krail.trip_planner.ui.savedtrips

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip_planner.ui.state.savedtrip.SavedTripUiEvent
import xyz.ksharma.krail.trip_planner.ui.state.savedtrip.SavedTripsState

@Composable
fun SavedTripsScreen(
    savedTripsState: SavedTripsState,
    modifier: Modifier = Modifier,
    onEvent: (SavedTripUiEvent) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = KrailTheme.colors.secondaryContainer)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Text(
            "SavedTripsScreen",
            style = KrailTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            "state: ${savedTripsState.trip}",
            style = KrailTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            "Load State",
            modifier = Modifier
                .padding(top = 16.dp)
                .clickable { onEvent(SavedTripUiEvent.LoadSavedTrips) })
    }
}
