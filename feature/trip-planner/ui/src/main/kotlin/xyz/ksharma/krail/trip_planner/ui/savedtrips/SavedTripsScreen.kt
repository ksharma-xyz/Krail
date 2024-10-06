package xyz.ksharma.krail.trip_planner.ui.savedtrips

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.components.TitleBar
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip_planner.ui.R
import xyz.ksharma.krail.trip_planner.ui.components.SearchStopRow
import xyz.ksharma.krail.trip_planner.ui.state.savedtrip.SavedTripUiEvent
import xyz.ksharma.krail.trip_planner.ui.state.savedtrip.SavedTripsState

@Composable
fun SavedTripsScreen(
    savedTripsState: SavedTripsState,
    modifier: Modifier = Modifier,
    fromButtonClick: () -> Unit = {},
    toButtonClick: () -> Unit = {},
    onSearchButtonClick: () -> Unit = {},
    onEvent: (SavedTripUiEvent) -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = KrailTheme.colors.secondaryContainer)
    ) {

        LazyColumn(modifier = Modifier, contentPadding = PaddingValues(bottom = 300.dp)) {
            item {
                TitleBar(title = stringResource(R.string.saved_trips_screen_title))
            }
        }

        SearchStopRow(
            modifier = Modifier.align(Alignment.BottomCenter),
            fromButtonClick = fromButtonClick,
            toButtonClick = toButtonClick,
            onSearchButtonClick = onSearchButtonClick,
        )
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
