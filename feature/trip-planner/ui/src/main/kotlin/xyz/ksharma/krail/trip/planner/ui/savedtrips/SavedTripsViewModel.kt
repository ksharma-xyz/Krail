package xyz.ksharma.krail.trip.planner.ui.savedtrips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.sandook.di.SandookFactory
import xyz.ksharma.krail.trip.planner.ui.state.savedtrip.SavedTripUiEvent
import xyz.ksharma.krail.trip.planner.ui.state.savedtrip.SavedTripsState
import xyz.ksharma.krail.trip.planner.ui.state.timetable.Trip
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class SavedTripsViewModel @Inject constructor(
    sandookFactory: SandookFactory,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val sandook: Sandook = sandookFactory.create(SandookFactory.SandookKey.SAVED_TRIP)

    private val _uiState: MutableStateFlow<SavedTripsState> = MutableStateFlow(SavedTripsState())
    val uiState: StateFlow<SavedTripsState> = _uiState

    private val _isActive: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isActive: StateFlow<Boolean> = _isActive.onStart {
        loadSavedTrips()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(ANR_TIMEOUT.inWholeMilliseconds),
        initialValue = true,
    )

    private fun loadSavedTrips() {
        viewModelScope.launch(context = ioDispatcher) {
            val trips = sandook.keys().mapNotNull { key ->
                val tripString = sandook.getString(key, null)
                tripString?.let { tripJsonString ->
                    Trip.fromJsonString(tripJsonString)
                }
            }.toImmutableList()
            if (!trips.isEmpty()) {
                updateUiState { copy(savedTrips = trips) }
            }
        }
    }

    fun onEvent(event: SavedTripUiEvent) {
        when (event) {
            is SavedTripUiEvent.DeleteSavedTrip -> onDeleteSavedTrip(event.trip)
            is SavedTripUiEvent.SavedTripClicked -> onSavedTripClicked(event.trip)
        }
    }

    private fun onDeleteSavedTrip(savedTrip: Trip) {
        Timber.d("onDeleteSavedTrip: $savedTrip")
        viewModelScope.launch(context = ioDispatcher) {
            sandook.remove(key = savedTrip.tripId)
            loadSavedTrips()
        }
    }

    private fun onSavedTripClicked(savedTrip: Trip) {
        Timber.d("onSavedTripClicked: $savedTrip")
    }

    private fun updateUiState(block: SavedTripsState.() -> SavedTripsState) {
        _uiState.update(block)
    }

    companion object {
        private val ANR_TIMEOUT = 5.seconds
    }
}
