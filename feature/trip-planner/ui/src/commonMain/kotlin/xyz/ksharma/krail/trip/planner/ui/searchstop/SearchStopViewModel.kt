package xyz.ksharma.krail.trip.planner.ui.searchstop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.ksharma.krail.trip.planner.network.api.service.getHttpClient
import xyz.ksharma.krail.trip.planner.network.api.service.stop_finder.fetchStop
import xyz.ksharma.krail.trip.planner.ui.searchstop.StopResultMapper.toStopResults
import xyz.ksharma.krail.trip.planner.ui.state.searchstop.SearchStopState
import xyz.ksharma.krail.trip.planner.ui.state.searchstop.SearchStopUiEvent

class SearchStopViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<SearchStopState> = MutableStateFlow(SearchStopState())
    val uiState: StateFlow<SearchStopState> = _uiState

    private val httpClient = getHttpClient()

    fun onEvent(event: SearchStopUiEvent) {
        when (event) {
            is SearchStopUiEvent.SearchTextChanged -> onSearchTextChanged(event.query)
        }
    }

    private fun onSearchTextChanged(query: String) {
        //Timber.d("onSearchTextChanged: $query")
        updateUiState { displayLoading() }

        viewModelScope.launch {
            runCatching {
                val response =
                    fetchStop(httpClient = httpClient, stopSearchQuery = query)
                println("response VM: $response")

                val results = response.toStopResults()
                println("results: $results")

                updateUiState { displayData(results) }
            }.getOrElse {
                updateUiState { displayError() }
            }
        }
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
