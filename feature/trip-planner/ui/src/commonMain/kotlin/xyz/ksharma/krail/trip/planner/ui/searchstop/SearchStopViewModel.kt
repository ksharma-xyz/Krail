package xyz.ksharma.krail.trip.planner.ui.searchstop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.ksharma.krail.core.analytics.Analytics
import xyz.ksharma.krail.core.analytics.AnalyticsScreen
import xyz.ksharma.krail.core.analytics.event.AnalyticsEvent
import xyz.ksharma.krail.core.analytics.event.trackScreenViewEvent
import xyz.ksharma.krail.core.log.log
import xyz.ksharma.krail.core.remote_config.flag.Flag
import xyz.ksharma.krail.core.remote_config.flag.FlagKeys
import xyz.ksharma.krail.core.remote_config.flag.asBoolean
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.sandook.SelectProductClassesForStop
import xyz.ksharma.krail.trip.planner.network.api.service.TripPlanningService
import xyz.ksharma.krail.trip.planner.ui.searchstop.StopResultMapper.toStopResults
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.TransportModeSortOrder
import xyz.ksharma.krail.trip.planner.ui.state.searchstop.SearchStopState
import xyz.ksharma.krail.trip.planner.ui.state.searchstop.SearchStopUiEvent

class SearchStopViewModel(
    private val tripPlanningService: TripPlanningService,
    private val analytics: Analytics,
    private val sandook: Sandook,
    private val flag: Flag,
) : ViewModel() {

    private val _uiState: MutableStateFlow<SearchStopState> = MutableStateFlow(SearchStopState())
    val uiState: StateFlow<SearchStopState> = _uiState
        .onStart {
            analytics.trackScreenViewEvent(screen = AnalyticsScreen.SearchStop)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SearchStopState())

    private var searchJob: Job? = null

    private val isLocalStopsEnabled: Boolean by lazy {
        flag.getFlagValue(FlagKeys.LOCAL_STOPS_ENABLED.key).asBoolean()
    }

    fun onEvent(event: SearchStopUiEvent) {
        when (event) {
            is SearchStopUiEvent.SearchTextChanged -> onSearchTextChanged(event.query)

            is SearchStopUiEvent.StopSelected -> {
                analytics.track(AnalyticsEvent.StopSelectedEvent(stopId = event.stopItem.stopId))
            }
        }
    }

    private fun onSearchTextChanged(query: String) {
        //log(("onSearchTextChanged: $query")
        updateUiState { displayLoading() }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(150)
            runCatching {
                val stopResults = fetchStopResults(query)
                updateUiState { displayData(stopResults) }
            }.getOrElse {
                delay(1500) // buffer for API response before displaying error.
                updateUiState { displayError() }
            }
        }
    }

    private suspend fun fetchStopResults(query: String): List<SearchStopState.StopResult> =
        if (isLocalStopsEnabled) {
            log("fetchStopResults from LOCAL_STOPS")
            val resultsDb: List<SelectProductClassesForStop> =
                sandook.selectStops(stopName = query, excludeProductClassList = listOf())

            val results = resultsDb
                .map { it.toStopResult() }
                .let {
                    filterProductClasses(
                        stopResults = it,
                        excludedProductClasses = listOf(TransportMode.Ferry().productClass).toImmutableList()
                    )
                }
                .let(::prioritiseStops)
                .take(50)

            results
        } else {
            log("fetchStopResults from REMOTE")
            val response = tripPlanningService.stopFinder(stopSearchQuery = query)
            log("response VM: $response")
            response.toStopResults()
        }

    // TODO - move to another file and add UT for it. Inject and use.
    private fun prioritiseStops(stopResults: List<SearchStopState.StopResult>): List<SearchStopState.StopResult> {
        val sortedTransportModes = TransportMode.sortedValues(TransportModeSortOrder.PRIORITY)
        val transportModePriorityMap = sortedTransportModes.mapIndexed { index, transportMode ->
            transportMode.productClass to index
        }.toMap()

        // TODO - these should come from Firebase config and have only these hardcoded as fallback.
        val highPriorityStopIds = listOf(
            "200060",
            "200070",
            "200080",
            "206010",
            "2150106",
            "200017",
            "200039",
            "201016",
            "201039",
            "201080",
            "200066",
            "200030",
            "200046",
            "200050",
            )

        return stopResults.sortedWith(compareBy(
            { stopResult ->
                if (stopResult.stopId in highPriorityStopIds) 0 else 1
            },
            { stopResult ->
                stopResult.transportModeType.minOfOrNull {
                    transportModePriorityMap[it.productClass] ?: Int.MAX_VALUE
                } ?: Int.MAX_VALUE
            },
            { it.stopName }
        ))
    }

    private fun SearchStopState.displayData(stopsResult: List<SearchStopState.StopResult>) = copy(
        stops = stopsResult.toImmutableList(),
        isLoading = false,
        isError = false,
    )

    private fun SearchStopState.displayLoading() =
        copy(isLoading = true, isError = false)

    private fun SearchStopState.displayError() = copy(
        isLoading = false,
        stops = persistentListOf(),
        isError = true,
    )

    private fun updateUiState(block: SearchStopState.() -> SearchStopState) {
        _uiState.update(block)
    }
}

fun filterProductClasses(
    stopResults: List<SearchStopState.StopResult>,
    excludedProductClasses: List<Int>,
): List<SearchStopState.StopResult> {
    return stopResults.filter { stopResult ->
        val productClasses = stopResult.transportModeType.map { it.productClass }
        productClasses.any { it !in excludedProductClasses }
    }
}

/// TODO - move to mapper:
private fun SelectProductClassesForStop.toStopResult() = SearchStopState.StopResult(
    stopId = stopId,
    stopName = stopName,
    transportModeType = this.productClasses.split(",").mapNotNull {
        TransportMode.toTransportModeType(it.toInt())
    }.toImmutableList(),
)
