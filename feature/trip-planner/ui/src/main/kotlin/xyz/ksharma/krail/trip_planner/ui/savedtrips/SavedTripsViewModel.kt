package xyz.ksharma.krail.trip_planner.ui.savedtrips

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.ksharma.krail.trip_planner.ui.navigation.SearchStopFieldType
import xyz.ksharma.krail.trip_planner.ui.state.savedtrip.SavedTripUiEvent
import xyz.ksharma.krail.trip_planner.ui.state.savedtrip.SavedTripsState
import xyz.ksharma.krail.trip_planner.ui.state.searchstop.model.StopItem
import xyz.ksharma.krail.trip_planner.ui.state.searchstop.model.StopItem.Companion.fromJsonString
import xyz.ksharma.krail.trip_planner.ui.timetable.TimeTableViewModel
import xyz.ksharma.krail.trip_planner.ui.timetable.TimeTableViewModel.Companion
import javax.inject.Inject

@HiltViewModel
class SavedTripsViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) :
    ViewModel() {

    private val _uiState: MutableStateFlow<SavedTripsState> = MutableStateFlow(SavedTripsState())
    val uiState: StateFlow<SavedTripsState> = _uiState

    fun onEvent(event: SavedTripUiEvent) {
        when (event) {
            is SavedTripUiEvent.DeleteSavedTrip -> onDeleteSavedTrip(event.savedTrip)
            SavedTripUiEvent.LoadSavedTrips -> onLoadSavedTrips()
            is SavedTripUiEvent.SavedTripClicked -> onSavedTripClicked(event.savedTrip)
            SavedTripUiEvent.ReverseButtonClicked -> onReverseButtonClicked()
            is SavedTripUiEvent.FromStopFieldUpdated -> onFromStopFieldUpdated(event.fromStopItem)
            is SavedTripUiEvent.ToStopFieldUpdated -> onToStopFieldUpdated(event.toStopItem)
        }
    }

    private fun onFromStopFieldUpdated(fromStopItem: StopItem) {
        Timber.d("onFromStopFieldUpdated: ${fromStopItem.stopName}")
        viewModelScope.launch {
            updateUiState { copy(fromStopItem = fromStopItem) }
            savedStateHandle[SearchStopFieldType.FROM.key] = fromStopItem.toJsonString()
        }
    }

    private fun onToStopFieldUpdated(toStopItem: StopItem) {
        Timber.d("onToStopFieldUpdated: ${toStopItem.stopName}")
        viewModelScope.launch {
            updateUiState { copy(toStopItem = toStopItem) }
            savedStateHandle[SearchStopFieldType.TO.key] = toStopItem.toJsonString()
        }
    }

    private fun onReverseButtonClicked() {
        val currentFromStopItem = uiState.value.fromStopItem
        val currentToStopItem = uiState.value.toStopItem

        updateUiState {
            copy(fromStopItem = currentToStopItem, toStopItem = currentFromStopItem)
        }
        // TODO - figure why we need it
        savedStateHandle[SearchStopFieldType.TO.key] = currentFromStopItem?.toJsonString()
        savedStateHandle[SearchStopFieldType.FROM.key] = currentToStopItem?.toJsonString()
    }

    private fun onSavedTripClicked(savedTrip: String) {
        Timber.d("onSavedTripClicked")
    }

    private fun onLoadSavedTrips() {
        Timber.d("onLoadSavedTrips")
        updateUiState { copy() }
    }

    private fun onDeleteSavedTrip(savedTrip: String) {
        Timber.d("onDeleteSavedTrip")
    }

    private fun updateUiState(block: SavedTripsState.() -> SavedTripsState) {
        _uiState.update(block)
    }

    companion object {
        private const val ANR_TIMEOUT = 5000L
    }
}
