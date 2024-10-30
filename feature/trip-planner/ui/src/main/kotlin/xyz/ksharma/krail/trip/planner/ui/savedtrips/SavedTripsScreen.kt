package xyz.ksharma.krail.trip.planner.ui.savedtrips

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.components.TitleBar
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip.planner.ui.R
import xyz.ksharma.krail.trip.planner.ui.components.SavedTripCard
import xyz.ksharma.krail.trip.planner.ui.components.SearchStopRow
import xyz.ksharma.krail.trip.planner.ui.components.hexToComposeColor
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.savedtrip.SavedTripUiEvent
import xyz.ksharma.krail.trip.planner.ui.state.savedtrip.SavedTripsState
import xyz.ksharma.krail.trip.planner.ui.state.searchstop.model.StopItem

@Composable
fun SavedTripsScreen(
    savedTripsState: SavedTripsState,
    modifier: Modifier = Modifier,
    fromStopItem: StopItem? = null,
    toStopItem: StopItem? = null,
    fromButtonClick: () -> Unit = {},
    toButtonClick: () -> Unit = {},
    onReverseButtonClick: () -> Unit = {},
    onSearchButtonClick: (StopItem?, StopItem?) -> Unit = { _, _ -> },
    onEvent: (SavedTripUiEvent) -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = KrailTheme.colors.background)
            .statusBarsPadding(),
    ) {
        Column {
            TitleBar(title = {
                Text(text = stringResource(R.string.saved_trips_screen_title))
            })

            LazyColumn(
                contentPadding = PaddingValues(bottom = 300.dp),
            ) {
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                }

                items(
                    items = savedTripsState.savedTrips,
                    key = { it.fromStopId + it.toStopId },
                ) { trip ->

                    SavedTripCard(
                        trip = trip,
                        onStarClick = { onEvent(SavedTripUiEvent.DeleteSavedTrip(trip)) },
                        onCardClick = {
                            onSearchButtonClick(
                                StopItem(
                                    stopId = trip.fromStopId,
                                    stopName = trip.fromStopName,
                                ),
                                StopItem(
                                    stopId = trip.toStopId,
                                    stopName = trip.toStopName,
                                ),
                            )
                        },
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .animateItem(fadeOutSpec = tween(durationMillis = 500)),
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        SearchStopRow(
            modifier = Modifier.align(Alignment.BottomCenter),
            fromStopItem = fromStopItem,
            toStopItem = toStopItem,
            fromButtonClick = fromButtonClick,
            toButtonClick = toButtonClick,
            onReverseButtonClick = onReverseButtonClick,
            onSearchButtonClick = { onSearchButtonClick(null, null) },
            themeColor = TransportMode.Train().colorCode.hexToComposeColor(), // TODO - theme color
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
