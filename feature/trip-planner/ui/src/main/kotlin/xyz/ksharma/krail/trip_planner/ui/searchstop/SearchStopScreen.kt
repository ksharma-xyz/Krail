package xyz.ksharma.krail.trip_planner.ui.searchstop

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
    val keyboard = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboard?.show()
    }

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
            TextField(
                placeholder = "Search",
                modifier = Modifier.focusRequester(focusRequester)
            ) { value ->
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
        } else if (searchStopState.stops.isNotEmpty() && textFieldText.isNotBlank()) {
            searchStopState.stops.forEach { stop ->
                item {
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        Text(
                            text = stop.stopName,
                            style = KrailTheme.typography.bodyMedium,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp)
                        ) {
                            stop.mode.map { it.modeType }.forEach { modeType ->
                                modeType?.toDisplayModeType()?.let { type ->
                                    TransportModeIcon(transportModeType = type)
                                }
                            }
                        }

                        // Divider Component
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(color = KrailTheme.colors.onBackground.copy(alpha = 0.2f))
                        )
                    }
                }
            }
        } else {
            item {
                Text(text = "Display Recent Search", modifier = Modifier.padding(vertical = 16.dp))
            }
        }
    }
}

private fun TransportMode.TransportModeType.toDisplayModeType() = when (this) {
    TransportMode.TransportModeType.Bus, TransportMode.TransportModeType.SchoolBus -> TransportModeType.Bus
    TransportMode.TransportModeType.Ferry -> TransportModeType.Ferry
    TransportMode.TransportModeType.LightRail -> TransportModeType.LightRail
    TransportMode.TransportModeType.Metro -> TransportModeType.Metro
    TransportMode.TransportModeType.Train -> TransportModeType.Train
    TransportMode.TransportModeType.Coach -> TransportModeType.Coach
    else -> null
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
