package xyz.ksharma.krail.trip.planner.ui.savedtrips

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.taj.LocalThemeContentColor
import xyz.ksharma.krail.taj.components.Text
import xyz.ksharma.krail.taj.components.TitleBar
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.trip.planner.ui.components.ErrorMessage
import xyz.ksharma.krail.trip.planner.ui.components.SavedTripCard
import xyz.ksharma.krail.trip.planner.ui.components.SearchStopRow
import xyz.ksharma.krail.trip.planner.ui.state.savedtrip.SavedTripUiEvent
import xyz.ksharma.krail.trip.planner.ui.state.savedtrip.SavedTripsState
import xyz.ksharma.krail.trip.planner.ui.state.searchstop.model.StopItem
import krail.feature.trip_planner.ui.generated.resources.Res
import krail.feature.trip_planner.ui.generated.resources.ic_reverse
import org.jetbrains.compose.resources.painterResource


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
    val themeContentColor by LocalThemeContentColor.current
    // TODO -  handle colors of status bar
/*    DisposableEffect(themeContentColor) {
        context.getActivityOrNull()?.let { activity ->
            (activity as ComponentActivity).enableEdgeToEdge(
                navigationBarStyle = SystemBarStyle.auto(
                    lightScrim = themeContentColor.hexToComposeColor().toArgb(),
                    darkScrim = themeContentColor.hexToComposeColor().toArgb(),
                ),
            )
        }
        onDispose {}
    }*/

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = KrailTheme.colors.surface)
            .statusBarsPadding(),
    ) {
        Column {
            TitleBar(title = {
                Text(text = "Saved Trips")
            })

            LazyColumn(
                contentPadding = PaddingValues(bottom = 300.dp),
            ) {
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                }

                if (savedTripsState.savedTrips.isEmpty()) {
                    item(key = "empty_state") {
                        ErrorMessage(
                            emoji = "🌟",
                            title = "Ready to roll, mate?",
                            message = "Star your fave trips and they'll be right here!",
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .animateItem(),
                        )
                    }
                } else {
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
                            primaryTransportMode = null, // TODO
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                    }
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
        )
    }
}

// region Previews

@Composable
private fun SavedTripsScreenPreview() {
    KrailTheme {
        SavedTripsScreen(savedTripsState = SavedTripsState())
    }
}

// endregion
