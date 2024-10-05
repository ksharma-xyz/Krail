package xyz.ksharma.krail.trip_planner.ui.searchstop

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import xyz.ksharma.krail.trip_planner.ui.state.searchstop.SearchStopState
import xyz.ksharma.krail.trip_planner.ui.state.searchstop.SearchStopUiEvent
import javax.inject.Inject

@HiltViewModel
class SearchStopViewModel @Inject constructor() : ViewModel() {

    private val _uiState: MutableStateFlow<SearchStopState> = MutableStateFlow(SearchStopState())
    val uiState: StateFlow<SearchStopState> = _uiState

    fun onEvent(event: SearchStopUiEvent) {
        when (event) {
            is SearchStopUiEvent.SearchStopFromQuery -> onSearchStopFromQuery(event.query)
        }
    }

    private fun onSearchStopFromQuery(query: String) {

    }

}
