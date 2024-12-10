package xyz.ksharma.krail.trip.planner.ui.searchstop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.ksharma.krail.trip.planner.network.api.service.TripPlanningService
import xyz.ksharma.krail.trip.planner.ui.searchstop.StopResultMapper.toStopResults
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.searchstop.SearchStopState
import xyz.ksharma.krail.trip.planner.ui.state.searchstop.SearchStopUiEvent

class SearchStopViewModel(private val tripPlanningService: TripPlanningService) : ViewModel() {

    private val _uiState: MutableStateFlow<SearchStopState> = MutableStateFlow(SearchStopState())
    val uiState: StateFlow<SearchStopState> = _uiState

    private var searchJob: Job? = null

    fun onEvent(event: SearchStopUiEvent) {
        when (event) {
            is SearchStopUiEvent.SearchTextChanged -> onSearchTextChanged(event.query)
        }
    }

    private fun onSearchTextChanged(query: String) {
        // Display local results immediately
        updateUiState { displayLoading() }
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            val localResults = processLocalStopResults(query)
            updateUiState { displayData(localResults) }

            println("NEW Stop Search")
            runCatching {
                val response = tripPlanningService.stopFinder(stopSearchQuery = query)
                println("response VM: $response")

                val networkResults = response.toStopResults()
                networkResults.forEach {
                    println("Network: stopId: ${it.stopId}, stopName: ${it.stopName}, transportModeType: ${it.transportModeType}")
                }
                val mergedResults = mergeResults(localResults, networkResults)
                mergedResults.forEach {
                    println("Merged: stopId: ${it.stopId}, stopName: ${it.stopName}, transportModeType: ${it.transportModeType}")
                }

                updateUiState { displayData(mergedResults) }
            }.getOrElse {
                delay(1500) // buffer for API response before displaying error.
                updateUiState { displayError() }
            }
        }
    }

    private fun processLocalStopResults(query: String): List<SearchStopState.StopResult> {
        val resultMap = LinkedHashMap<String, SearchStopState.StopResult>()

        val normalizedQuery = query.replace(" ", "").lowercase()
        fun normalizeStopName(name: String) = name.replace(" ", "").lowercase()

        // Filter metroStops, trainStops, ferryStops, and lightRailStops based on the normalized query
        val matchingMetroStops =
            metroStops.filter { normalizeStopName(it.value).contains(normalizedQuery) }
        val matchingTrainStops =
            trainStops.filter { normalizeStopName(it.value).contains(normalizedQuery) }
        val matchingFerryStops =
            ferryStops.filter { normalizeStopName(it.value).contains(normalizedQuery) }
        val matchingLightRailStops =
            lightRailStops.filter { normalizeStopName(it.value).contains(normalizedQuery) }

        // Create StopResult objects for matching metro stops
        matchingMetroStops.forEach { (id, name) ->
            val existingResult = resultMap[id]
            if (existingResult != null) {
                val combinedModes =
                    (existingResult.transportModeType + TransportMode.Metro()).toPersistentList()
                resultMap[id] = existingResult.copy(transportModeType = combinedModes)
            } else {
                resultMap[id] = SearchStopState.StopResult(
                    stopName = name,
                    stopId = id,
                    transportModeType = persistentListOf(TransportMode.Metro())
                )
            }
        }

        // Create StopResult objects for matching train stops
        matchingTrainStops.forEach { (id, name) ->
            val existingResult = resultMap[id]
            if (existingResult != null) {
                val combinedModes =
                    (existingResult.transportModeType + TransportMode.Train()).toPersistentList()
                resultMap[id] = existingResult.copy(transportModeType = combinedModes)
            } else {
                resultMap[id] = SearchStopState.StopResult(
                    stopName = name,
                    stopId = id,
                    transportModeType = persistentListOf(TransportMode.Train())
                )
            }
        }

        // Create StopResult objects for matching ferry stops
        matchingFerryStops.forEach { (id, name) ->
            val existingResult = resultMap[id]
            if (existingResult != null) {
                val combinedModes =
                    (existingResult.transportModeType + TransportMode.Ferry()).toPersistentList()
                resultMap[id] = existingResult.copy(transportModeType = combinedModes)
            } else {
                resultMap[id] = SearchStopState.StopResult(
                    stopName = name,
                    stopId = id,
                    transportModeType = persistentListOf(TransportMode.Ferry())
                )
            }
        }

        matchingLightRailStops.forEach { (id, name) ->
            val existingResult = resultMap[id]
            if (existingResult != null) {
                val combinedModes =
                    (existingResult.transportModeType + TransportMode.LightRail()).toPersistentList()
                resultMap[id] = existingResult.copy(transportModeType = combinedModes)
            } else {
                resultMap[id] = SearchStopState.StopResult(
                    stopName = name,
                    stopId = id,
                    transportModeType = persistentListOf(TransportMode.LightRail())
                )
            }
        }

        // Sort results: exact matches first, then starts with query, then contains query
        return resultMap.values.sortedWith(compareBy(
            { !it.stopName.equals(query, ignoreCase = true) },
            { !it.stopName.startsWith(query, ignoreCase = true) },
            { it.stopName }
        ))
    }

    private fun mergeResults(
        localResults: List<SearchStopState.StopResult>,
        networkResults: List<SearchStopState.StopResult>,
    ): List<SearchStopState.StopResult> {
        val resultMap = LinkedHashMap<String, SearchStopState.StopResult>()

        (localResults + networkResults).forEach { result ->
            val existingResult = resultMap[result.stopId]
            if (existingResult != null) {
                val combinedModes =
                    (existingResult.transportModeType + result.transportModeType).toPersistentList()
                resultMap[result.stopId] = existingResult.copy(transportModeType = combinedModes)
            } else {
                resultMap[result.stopId] = result
            }
        }

        return resultMap.values.toList()
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
