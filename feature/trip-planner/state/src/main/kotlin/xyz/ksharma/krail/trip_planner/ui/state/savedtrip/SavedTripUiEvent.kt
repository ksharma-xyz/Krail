package xyz.ksharma.krail.trip_planner.ui.state.savedtrip

sealed interface SavedTripUiEvent {
    data object LoadSavedTrips : SavedTripUiEvent
    data class SavedTripClicked(val savedTrip: String) : SavedTripUiEvent
    data class DeleteSavedTrip(val savedTrip: String) : SavedTripUiEvent
}
