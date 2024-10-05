package xyz.ksharma.krail.trip_planner.ui.timetable

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableState
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableUiEvent
import javax.inject.Inject

@HiltViewModel
class TimeTableViewModel @Inject constructor() : ViewModel() {

    private val _uiState: MutableStateFlow<TimeTableState> = MutableStateFlow(TimeTableState())
    val uiState: StateFlow<TimeTableState> = _uiState

    fun onEvent(event: TimeTableUiEvent) {
        when (event) {
            TimeTableUiEvent.LoadTimeTable -> onLoadTimeTable()
        }
    }

    private fun onLoadTimeTable() {

    }
}
