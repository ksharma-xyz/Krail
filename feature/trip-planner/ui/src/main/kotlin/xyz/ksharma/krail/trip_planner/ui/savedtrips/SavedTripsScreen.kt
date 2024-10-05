package xyz.ksharma.krail.trip_planner.ui.savedtrips

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip_planner.ui.R
import xyz.ksharma.krail.trip_planner.ui.components.SearchStopRow
import xyz.ksharma.krail.trip_planner.ui.state.savedtrip.SavedTripUiEvent
import xyz.ksharma.krail.trip_planner.ui.state.savedtrip.SavedTripsState

@Composable
fun SavedTripsScreen(
    savedTripsState: SavedTripsState,
    modifier: Modifier = Modifier,
    onEvent: (SavedTripUiEvent) -> Unit = {},
) {
    LazyColumn {
        item {
            Text(
                text = stringResource(R.string.saved_trips_screen_title),
                style = KrailTheme.typography.headlineLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .systemBarsPadding()
            )
        }

        item {
            SearchStopRow(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

// region Previews

@PreviewLightDark
@Composable
private fun SavedTripsScreenPreview() {
    KrailTheme {
        SavedTripsScreen(savedTripsState = SavedTripsState())
    }
}

// endregion
