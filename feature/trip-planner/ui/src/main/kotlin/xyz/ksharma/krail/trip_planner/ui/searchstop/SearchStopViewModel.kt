package xyz.ksharma.krail.trip_planner.ui.searchstop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
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
        Timber.d("query: %s", query)
        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(isLoading = true, stops = persistentListOf(), isError = false)

            tripPlanningRepository.stopFinder(stopSearchQuery = query)
                .onSuccess { response: StopFinderResponse ->
                    response.toStopsResult()
                    _uiState.value =
                        _uiState.value.copy(
                            stops = response.toStopsResult().stops.toImmutableList(),
                            isLoading = false,
                            isError = false
                        )
                }.onFailure {
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            stops = persistentListOf(),
                            isError = true
                        )
                }
        }
    }
}

private fun StopFinderResponse.toStopsResult() =
    StopsResult(
        stops = locations.orEmpty().mapNotNull { it.name }.distinct()
    )

data class StopsResult(val stops: List<String> = emptyList())
