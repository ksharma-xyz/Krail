package xyz.ksharma.krail.trip_planner.ui.searchstop

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest
import timber.log.Timber
import xyz.ksharma.krail.design.system.components.Divider
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.components.TextField
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip_planner.ui.components.StopSearchListItem
import xyz.ksharma.krail.trip_planner.ui.state.searchstop.SearchStopState
import xyz.ksharma.krail.trip_planner.ui.state.searchstop.SearchStopUiEvent
import xyz.ksharma.krail.trip_planner.ui.state.searchstop.model.StopItem

/**
 * TODO - implement scroll to top, when too many search results are displayed.
 */
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
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        stickyHeader {
            TextField(
                placeholder = "Search",
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 12.dp),
            ) { value ->
                Timber.d("value: $value")
                textFieldText = value.toString()
            }
        }

        if (searchStopState.isLoading) {
            item {
                Text(
                    text = "Loading...",
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }
        } else if (searchStopState.isError) {
            item {
                Text(
                    text = "Error",
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }
        } else if (searchStopState.stops.isNotEmpty() && textFieldText.isNotBlank()) {

            searchStopState.stops.forEach { stop ->
                item {
                    StopSearchListItem(
                        stopItem = StopItem(
                            stopId = stop.stopId,
                            stopName = stop.stopName,
                            transportModes = stop.transportModeType.toImmutableSet(),
                        ),
                    ) { stopItem -> onEvent(SearchStopUiEvent.StopSelected(stopItem)) }

                    Divider()
                }
            }
        } else {
            item {
                Text(
                    text = "Display Recent Search",
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }
        }
    }
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
