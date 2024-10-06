package xyz.ksharma.krail.trip_planner.ui.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableState
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableUiEvent
import javax.inject.Inject

@HiltViewModel
class TimeTableViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState: MutableStateFlow<TimeTableState> = MutableStateFlow(TimeTableState())
    val uiState: StateFlow<TimeTableState> = _uiState

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> =
        _isLoading.stateIn(viewModelScope, SharingStarted.WhileSubscribed(ANR_TIMEOUT), true)

    fun onEvent(event: TimeTableUiEvent) {
        when (event) {
            is TimeTableUiEvent.LoadTimeTable -> onLoadTimeTable(event.fromStopId, event.toStopId)
        }
    }

    private fun onLoadTimeTable(fromStopId: String?, toStopId: String?) {
        Timber.d("loadTimeTable API Call- fromStopItem: $fromStopId, toStopItem: $toStopId")
    }

    companion object {
        private const val ANR_TIMEOUT = 5000L
    }
}
