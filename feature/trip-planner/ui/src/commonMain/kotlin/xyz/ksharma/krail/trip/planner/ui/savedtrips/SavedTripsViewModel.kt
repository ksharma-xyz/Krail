package xyz.ksharma.krail.trip.planner.ui.savedtrips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.ksharma.krail.core.analytics.Analytics
import xyz.ksharma.krail.core.analytics.AnalyticsScreen
import xyz.ksharma.krail.core.analytics.event.trackScreenViewEvent
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.sandook.SavedTrip
import xyz.ksharma.krail.trip.planner.ui.state.savedtrip.SavedTripUiEvent
import xyz.ksharma.krail.trip.planner.ui.state.savedtrip.SavedTripsState
import xyz.ksharma.krail.trip.planner.ui.state.timetable.Trip

class SavedTripsViewModel(
    private val sandook: Sandook,
    private val analytics: Analytics,
) : ViewModel() {

    private val _uiState: MutableStateFlow<SavedTripsState> = MutableStateFlow(SavedTripsState())
    val uiState: StateFlow<SavedTripsState> = _uiState
        .onStart {
            analytics.trackScreenViewEvent(screen = AnalyticsScreen.SavedTrips)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SavedTripsState())

    private fun loadSavedTrips() {
        viewModelScope.launch(context = Dispatchers.IO) {
            updateUiState { copy(isLoading = true) }
            val trips = mutableSetOf<Trip>()

            val savedTrips = sandook.selectAllTrips()
            println("SavedTrips: $savedTrips")
            savedTrips.forEachIndexed { index, savedTrip ->
                val trip = savedTrip.toTrip()
                println("Trip: #$index $trip")
                trips.add(savedTrip.toTrip())
            }

            trips.addAll(savedTrips.map { savedTrip -> savedTrip.toTrip() })

            println("SavedTrips: ${trips.size} number")
            trips.forEachIndexed { index, trip ->
                println("\t SavedTrip: #$index ${trip.fromStopName} -> ${trip.toStopName}")
            }

            updateUiState { copy(savedTrips = trips.toImmutableList(), isLoading = false) }
        }
    }

    fun onEvent(event: SavedTripUiEvent) {
        when (event) {
            is SavedTripUiEvent.DeleteSavedTrip -> onDeleteSavedTrip(event.trip)
            SavedTripUiEvent.LoadSavedTrips -> loadSavedTrips()
        }
    }

    private fun onDeleteSavedTrip(savedTrip: Trip) {
        println("onDeleteSavedTrip: $savedTrip")
        viewModelScope.launch(context = Dispatchers.IO) {
            sandook.deleteTrip(tripId = savedTrip.tripId)
            loadSavedTrips()
        }
    }

    private fun updateUiState(block: SavedTripsState.() -> SavedTripsState) {
        _uiState.update(block)
    }
}

private fun SavedTrip.toTrip(): Trip = Trip(
    fromStopId = fromStopId,
    fromStopName = fromStopName,
    toStopId = toStopId,
    toStopName = toStopName,
)
