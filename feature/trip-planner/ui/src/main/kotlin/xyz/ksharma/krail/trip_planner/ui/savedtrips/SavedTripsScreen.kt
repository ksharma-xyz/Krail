package xyz.ksharma.krail.trip_planner.ui.savedtrips

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = KrailTheme.colors.background)
    ) {
        SavedTripsScreenContent(modifier.fillMaxSize())

        SearchStopRow(
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
fun SavedTripsScreenContent(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {

        repeat(10) {
            item {
                Text(
                    text = stringResource(R.string.saved_trips_screen_title),
                    style = KrailTheme.typography.headlineLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }

        }
        item {
            Spacer(modifier = Modifier.height(200.dp))
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
