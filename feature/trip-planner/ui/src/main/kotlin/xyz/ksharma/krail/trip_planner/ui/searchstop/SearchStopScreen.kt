package xyz.ksharma.krail.trip_planner.ui.searchstop

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest
import timber.log.Timber
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.components.TextField
import xyz.ksharma.krail.design.system.components.TransportModeIcon
import xyz.ksharma.krail.design.system.model.TransportModeType
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip_planner.domain.model.TransportMode
import xyz.ksharma.krail.trip_planner.ui.state.searchstop.SearchStopState
import xyz.ksharma.krail.trip_planner.ui.state.searchstop.SearchStopUiEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchStopScreen(
    searchStopState: SearchStopState,
    modifier: Modifier = Modifier,
    onEvent: (SearchStopUiEvent) -> Unit = {},
) {
    var textFieldText: String by remember { mutableStateOf("") }

    LaunchedEffect(textFieldText) {
        snapshotFlow { textFieldText }
            .distinctUntilChanged()
            .debounce(600)
            .filter { it.isNotBlank() }
            .mapLatest { text -> onEvent(SearchStopUiEvent.SearchTextChanged(text)) }
            .collect()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = KrailTheme.colors.background)
            .systemBarsPadding(),
        contentPadding = PaddingValues(16.dp)
    ) {
        stickyHeader {
            TextField(placeholder = "Search") { value ->
                Timber.d("value: $value")
                textFieldText = value.toString()
            }
        }

        if (searchStopState.isLoading) {
            item {
                Text(text = "Loading...")
            }
        } else if (searchStopState.isError) {
            item {
                Text(text = "Error")
            }
        } else if (searchStopState.stops.isNotEmpty()) {
            searchStopState.stops.forEach { stop ->
                item {
                    Row(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {

                        //stop.mode.map { it.toDisplayModeType() }

//                        TransportModeIcon(transportModeType = stop.mode.toDisplayModeType())
                        Text(
                            text = stop.stopName + " - ${stop.mode.map { it.modeType }}",
                            modifier = Modifier.padding()
                        )
                    }
                }
            }
        } else {
            item {
                Text(text = "No stops found")
            }
        }

    }
}

fun TransportMode.TransportModeType.toDisplayModeType() = when (this) {
    TransportMode.TransportModeType.Bus -> TransportModeType.Bus
    TransportMode.TransportModeType.Ferry -> TransportModeType.Ferry
    TransportMode.TransportModeType.LightRail -> TransportModeType.LightRail
    TransportMode.TransportModeType.Metro -> TransportModeType.Metro
    TransportMode.TransportModeType.SydneyTrain -> TransportModeType.Train
    TransportMode.TransportModeType.IntercityTrain,
    TransportMode.TransportModeType.AirportTrain,
    TransportMode.TransportModeType.Coach,
    TransportMode.TransportModeType.OnDemand,
    TransportMode.TransportModeType.NightRide,
    TransportMode.TransportModeType.Shuttle,
    TransportMode.TransportModeType.None,
    -> null
}

// region Previews

@PreviewLightDark
@Composable
private fun SearchStopScreenPreview() {
    KrailTheme {
        SearchStopScreen(searchStopState = SearchStopState())
    }
}

// endregion
