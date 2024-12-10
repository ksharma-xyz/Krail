package xyz.ksharma.krail.trip.planner.ui.searchstop

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest
import kotlinx.datetime.Clock
import xyz.ksharma.krail.taj.LocalThemeColor
import xyz.ksharma.krail.taj.components.Divider
import xyz.ksharma.krail.taj.components.TextField
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.trip.planner.ui.components.ErrorMessage
import xyz.ksharma.krail.trip.planner.ui.components.StopSearchListItem
import xyz.ksharma.krail.trip.planner.ui.components.backgroundColorOf
import xyz.ksharma.krail.trip.planner.ui.components.hexToComposeColor
import xyz.ksharma.krail.trip.planner.ui.components.loading.AnimatedDots
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.searchstop.SearchStopState
import xyz.ksharma.krail.trip.planner.ui.state.searchstop.SearchStopUiEvent
import xyz.ksharma.krail.trip.planner.ui.state.searchstop.model.StopItem

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@Composable
fun SearchStopScreen(
    searchStopState: SearchStopState,
    modifier: Modifier = Modifier,
    searchQuery: String = "",
    onBackClick: () -> Unit = {},
    onStopSelect: (StopItem) -> Unit = {},
    onEvent: (SearchStopUiEvent) -> Unit = {},
) {
    val themeColor by LocalThemeColor.current
    var textFieldText: String by remember { mutableStateOf(searchQuery) }
    val keyboard = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboard?.show()
    }

    LaunchedEffect(textFieldText) {
        snapshotFlow { textFieldText.trim() }
            .distinctUntilChanged()
            .debounce(250)
            .filter { it.isNotBlank() }
            .mapLatest { text ->
                // Timber.d("Query - $text")
                onEvent(SearchStopUiEvent.SearchTextChanged(text))
            }.collectLatest {}
    }

    var displayNoMatchFound by remember { mutableStateOf(false) }
    var lastQueryTime by remember { mutableLongStateOf(0L) }
    LaunchedEffect(
        key1 = textFieldText,
        key2 = searchStopState.stops,
        key3 = searchStopState.isLoading,
    ) {
        if (textFieldText.isNotBlank() && searchStopState.stops.isEmpty()) {
            // To ensure a smooth transition from the results state to the "No match found" state,
            // track the time of the last query. If new results come in during the delay period,
            // then lastQueryTime will be different, therefore, it will prevent
            // "No match found" message from being displayed.
            val currentQueryTime = Clock.System.now().toEpochMilliseconds()
            lastQueryTime = currentQueryTime
            delay(1000) // TODO - remove this logic
            if (lastQueryTime == currentQueryTime && searchStopState.stops.isEmpty()) {
                displayNoMatchFound = true
            }
        } else {
            displayNoMatchFound = false
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        backgroundColorOf(themeColor.hexToComposeColor()),
                        KrailTheme.colors.surface,
                    ),
                ),
            )
            .imePadding(),
    ) {
        TextField(
            placeholder = "Search here",
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(vertical = 12.dp)
                .focusRequester(focusRequester)
                .padding(horizontal = 16.dp),
            maxLength = 30,
            filter = { input ->
                input.filter { it.isLetterOrDigit() || it.isWhitespace() }
            },
            leadingIcon = {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        ) {
                            keyboard?.hide()
                            focusRequester.freeFocus()
                            onBackClick()
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        colorFilter = ColorFilter.tint(KrailTheme.colors.onSurface),
                        modifier = Modifier.size(24.dp),
                    )
                }
            }
        ) { value ->
            //Timber.d("value: $value")
            textFieldText = value.toString()
        }

        LazyColumn(
            contentPadding = PaddingValues(top = 0.dp, bottom = 48.dp),
        ) {
            item {
                Column(
                    modifier = Modifier.height(KrailTheme.typography.bodyLarge.fontSize.value.dp + 12.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    AnimatedVisibility(
                        visible = searchStopState.isLoading,
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        AnimatedDots(modifier = Modifier.fillMaxWidth())
                    }
                }
            }

            if (searchStopState.isError && textFieldText.isNotBlank() && searchStopState.isLoading.not()) {
                item(key = "Error") {
                    ErrorMessage(
                        title = "Eh! That's not looking right mate.",
                        message = "Let's try searching again.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItem(),
                    )
                }
            } else if (searchStopState.stops.isNotEmpty() && textFieldText.isNotBlank()) {
                items(
                    items = searchStopState.stops,
                    key = { it.stopId },
                ) { stop ->
                    StopSearchListItem(
                        stopId = stop.stopId,
                        stopName = stop.stopName,
                        transportModeSet = stop.transportModeType.toImmutableSet(),
                        textColor = KrailTheme.colors.label,
                        onClick = { stopItem ->
                            keyboard?.hide()
                            focusRequester.freeFocus()
                            onStopSelect(stopItem)
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                    )

                    Divider()
                }
            } else if (displayNoMatchFound && textFieldText.isNotBlank() && searchStopState.isLoading.not()) {
                // TODO -  drive logic from VM
                item(key = "no_match") {
                    ErrorMessage(
                        title = "No match found!",
                        message = if (textFieldText.length < 4) {
                            "Throw in a few more chars! \uD83D\uDE0E"
                        } else {
                            "Try tweaking your search. \uD83D\uDD0D✨"
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItem(),
                    )
                }
            }
        }
    }
}

// region Previews

@Composable
private fun PreviewSearchStopScreenLoading() {
    KrailTheme {
        val themeColor = remember { mutableStateOf(TransportMode.Bus().colorCode) }
        CompositionLocalProvider(LocalThemeColor provides themeColor) {
            SearchStopScreen(
                searchQuery = "Search Query",
                searchStopState = searchStopState.copy(isLoading = true),
            )
        }
    }
}

@Composable
private fun PreviewSearchStopScreenError() {
    KrailTheme {
        val themeColor = remember { mutableStateOf(TransportMode.Bus().colorCode) }
        CompositionLocalProvider(LocalThemeColor provides themeColor) {
            SearchStopScreen(
                searchQuery = "Search Query",
                searchStopState = searchStopState.copy(isLoading = false, isError = true),
            )
        }
    }
}

@Composable
private fun PreviewSearchStopScreenEmpty() {
    KrailTheme {
        val themeColor = remember { mutableStateOf(TransportMode.Bus().colorCode) }
        CompositionLocalProvider(LocalThemeColor provides themeColor) {
            SearchStopScreen(
                searchQuery = "Search Query",
                searchStopState = searchStopState.copy(
                    isLoading = false,
                    isError = false,
                    stops = persistentListOf(),
                ),
            )
        }
    }
}

@Composable
private fun PreviewSearchStopScreenTrain() {
    KrailTheme {
        val themeColor = remember { mutableStateOf(TransportMode.Train().colorCode) }
        CompositionLocalProvider(LocalThemeColor provides themeColor) {
            SearchStopScreen(
                searchQuery = "Search Query",
                searchStopState = searchStopState,
            )
        }
    }
}

@Composable
private fun PreviewSearchStopScreenCoach() {
    KrailTheme {
        val themeColor = remember { mutableStateOf(TransportMode.Coach().colorCode) }
        CompositionLocalProvider(LocalThemeColor provides themeColor) {
            SearchStopScreen(
                searchQuery = "Search Query",
                searchStopState = searchStopState,
            )
        }
    }
}

@Composable
private fun PreviewSearchStopScreenFerry() {
    KrailTheme {
        val themeColor = remember { mutableStateOf(TransportMode.Ferry().colorCode) }
        CompositionLocalProvider(LocalThemeColor provides themeColor) {
            SearchStopScreen(
                searchQuery = "Search Query",
                searchStopState = searchStopState,
            )
        }
    }
}

@Composable
private fun PreviewSearchStopScreenMetro() {
    KrailTheme {
        val themeColor = remember { mutableStateOf(TransportMode.Metro().colorCode) }
        CompositionLocalProvider(LocalThemeColor provides themeColor) {
            SearchStopScreen(
                searchQuery = "Search Query",
                searchStopState = searchStopState,
            )
        }
    }
}

@Composable
private fun PreviewSearchStopScreenLightRail() {
    KrailTheme {
        val themeColor = remember { mutableStateOf(TransportMode.LightRail().colorCode) }
        CompositionLocalProvider(LocalThemeColor provides themeColor) {
            SearchStopScreen(
                searchQuery = "Search Query",
                searchStopState = searchStopState,
            )
        }
    }
}

@Composable
private fun PreviewSearchStopScreenBus() {
    KrailTheme {
        val themeColor = remember { mutableStateOf(TransportMode.Bus().colorCode) }
        CompositionLocalProvider(LocalThemeColor provides themeColor) {
            SearchStopScreen(
                searchQuery = "Search Query",
                searchStopState = searchStopState,
            )
        }
    }
}

private val searchStopState = SearchStopState(
    isLoading = false,
    stops = persistentListOf(
        SearchStopState.StopResult(
            stopId = "123",
            stopName = "Stop Name",
            transportModeType = persistentListOf(TransportMode.Bus()),
        ),
        SearchStopState.StopResult(
            stopId = "235",
            stopName = "Stop Name",
            transportModeType = persistentListOf(TransportMode.Ferry()),
        ),
        SearchStopState.StopResult(
            stopId = "235",
            stopName = "Stop Name",
            transportModeType = persistentListOf(
                TransportMode.Train(),
                TransportMode.Bus(),
            ),
        ),
    ),
)

// endregion
