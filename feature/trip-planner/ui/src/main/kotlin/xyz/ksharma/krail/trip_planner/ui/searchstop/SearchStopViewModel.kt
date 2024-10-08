package xyz.ksharma.krail.trip_planner.ui.searchstop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.ksharma.krail.trip_planner.ui.searchstop.StopResultMapper.toStopResults
import xyz.ksharma.krail.trip_planner.network.api.model.StopFinderResponse
import xyz.ksharma.krail.trip_planner.network.api.repository.TripPlanningRepository
import xyz.ksharma.krail.trip_planner.ui.state.searchstop.SearchStopState
import xyz.ksharma.krail.trip_planner.ui.state.searchstop.SearchStopUiEvent
import javax.inject.Inject

@HiltViewModel
class SearchStopViewModel @Inject constructor(
    private val tripPlanningRepository: TripPlanningRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<SearchStopState> = MutableStateFlow(SearchStopState())
    val uiState: StateFlow<SearchStopState> = _uiState

    fun onEvent(event: SearchStopUiEvent) {
        when (event) {
            is SearchStopUiEvent.SearchTextChanged -> onSearchTextChanged(event.query)
        }
    }

    private fun onSearchTextChanged(query: String) {
        Timber.d("onSearchTextChanged: $query")
        updateUiState { displayLoading() }

        viewModelScope.launch {
            tripPlanningRepository.stopFinder(stopSearchQuery = query)
                .onSuccess { response: StopFinderResponse ->
                    updateUiState { displayData(response.toStopResults()) }
                }.onFailure {
                    updateUiState { displayError() }
                }
        }
    }

    private fun SearchStopState.displayData(stopsResult: List<SearchStopState.StopResult>) = copy(
        stops = stopsResult.toImmutableList(),
        isLoading = false,
        isError = false
    )

    private fun SearchStopState.displayLoading() =
        copy(isLoading = true, stops = persistentListOf(), isError = false)

    private fun SearchStopState.displayError() = copy(
        isLoading = false,
        stops = persistentListOf(),
        isError = true
    )

    private fun updateUiState(block: SearchStopState.() -> SearchStopState) {
        _uiState.update(block)
    }
}
