package xyz.ksharma.krail.trip.planner.ui.savedtrips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.ksharma.krail.sandook.RealSandook
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.trip.planner.ui.state.savedtrip.SavedTripUiEvent
import xyz.ksharma.krail.trip.planner.ui.state.savedtrip.SavedTripsState
import xyz.ksharma.krail.trip.planner.ui.state.timetable.Trip

class SavedTripsViewModel : ViewModel() {
    private val sandook: Sandook = RealSandook()

    private val _uiState: MutableStateFlow<SavedTripsState> = MutableStateFlow(SavedTripsState())
    val uiState: StateFlow<SavedTripsState> = _uiState

    private fun loadSavedTrips() {
        println("loadSavedTrips, keys: ${sandook.keys()}")
        viewModelScope.launch(context = Dispatchers.IO) {
            val trips = persistentListOf<Trip>()
            sandook.keys().mapNotNull { key -> /// ERROR HERE TODO - keys mismatch
                val tripString = sandook.getString(key, null)
                tripString?.let { tripJsonString ->
                    Trip.fromJsonString(tripJsonString)
                }
            }.toImmutableList()

            println( "SavedTrips: ${trips.size} number")
            trips.forEachIndexed { index, trip ->
                println("\t SavedTrip: #$index ${trip.fromStopName} -> ${trip.toStopName}")
            }

            updateUiState { copy(savedTrips = trips) }
        }
    }

    fun onEvent(event: SavedTripUiEvent) {
        when (event) {
            is SavedTripUiEvent.DeleteSavedTrip -> onDeleteSavedTrip(event.trip)
            SavedTripUiEvent.LoadSavedTrips -> loadSavedTrips()
        }
    }

    private fun onDeleteSavedTrip(savedTrip: Trip) {
//        Timber.d("onDeleteSavedTrip: $savedTrip")
        viewModelScope.launch(context = Dispatchers.IO) {
            sandook.remove(key = savedTrip.tripId)
            loadSavedTrips()
        }
    }

    private fun updateUiState(block: SavedTripsState.() -> SavedTripsState) {
        _uiState.update(block)
    }
}
