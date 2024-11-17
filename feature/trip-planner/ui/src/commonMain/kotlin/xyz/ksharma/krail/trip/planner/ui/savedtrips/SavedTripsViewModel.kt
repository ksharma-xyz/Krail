package xyz.ksharma.krail.trip.planner.ui.savedtrips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher
import xyz.ksharma.krail.trip.planner.ui.state.savedtrip.SavedTripUiEvent
import xyz.ksharma.krail.trip.planner.ui.state.savedtrip.SavedTripsState
import xyz.ksharma.krail.trip.planner.ui.state.timetable.Trip

class SavedTripsViewModel (
//    sandookFactory: SandookFactory,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

//    private val sandook: Sandook = sandookFactory.create(SandookFactory.SandookKey.SAVED_TRIP)

    private val _uiState: MutableStateFlow<SavedTripsState> = MutableStateFlow(SavedTripsState())
    val uiState: StateFlow<SavedTripsState> = _uiState

    private fun loadSavedTrips() {
        viewModelScope.launch(context = ioDispatcher) {
            val trips = persistentListOf<Trip>() /*sandook.keys().mapNotNull { key ->
                val tripString = sandook.getString(key, null)
                tripString?.let { tripJsonString ->
                    Trip.fromJsonString(tripJsonString)
                }
            }.toImmutableList()*/

            trips.forEachIndexed { index, trip ->
                //Timber.d("\t SavedTrip: #$index ${trip.fromStopName} -> ${trip.toStopName}")
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
        viewModelScope.launch(context = ioDispatcher) {
//            sandook.remove(key = savedTrip.tripId)
            loadSavedTrips()
        }
    }

    private fun updateUiState(block: SavedTripsState.() -> SavedTripsState) {
        _uiState.update(block)
    }
}
