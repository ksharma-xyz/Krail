package xyz.ksharma.krail.trip_planner.ui.savedtrips

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import xyz.ksharma.krail.trip_planner.ui.state.savedtrip.SavedTripUiEvent
import xyz.ksharma.krail.trip_planner.ui.state.savedtrip.SavedTripsState
import javax.inject.Inject

@HiltViewModel
class SavedTripsViewModel @Inject constructor() : ViewModel() {

    private val _uiState: MutableStateFlow<SavedTripsState> = MutableStateFlow(SavedTripsState())
    val uiState: StateFlow<SavedTripsState> = _uiState

    fun onEvent(event: SavedTripUiEvent) {
        when (event) {
            is SavedTripUiEvent.DeleteSavedTrip -> onDeleteSavedTrip(event.savedTrip)
            SavedTripUiEvent.LoadSavedTrips -> onLoadSavedTrips()
            is SavedTripUiEvent.SavedTripClicked -> onSavedTripClicked(event.savedTrip)
        }
    }

    private fun onSavedTripClicked(savedTrip: String) {
        Timber.d("onSavedTripClicked")
    }

    private fun onLoadSavedTrips() {
        Timber.d("onLoadSavedTrips")

        updateUiState {
            copy(trip = "Central to Town Hall")
        }
    }

    private fun onDeleteSavedTrip(savedTrip: String) {
        Timber.d("onDeleteSavedTrip")
    }

    private fun updateUiState(block: SavedTripsState.() -> SavedTripsState) {
        _uiState.update(block)
    }
}