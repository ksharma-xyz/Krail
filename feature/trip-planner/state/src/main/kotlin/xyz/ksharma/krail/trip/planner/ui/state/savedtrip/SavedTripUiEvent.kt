package xyz.ksharma.krail.trip.planner.ui.state.savedtrip

import xyz.ksharma.krail.trip.planner.ui.state.searchstop.model.StopItem

sealed interface SavedTripUiEvent {
    data object LoadSavedTrips : SavedTripUiEvent
    data class SavedTripClicked(val savedTrip: String) : SavedTripUiEvent
    data class DeleteSavedTrip(val savedTrip: String) : SavedTripUiEvent
    data class OnSearchButtonClicked(val fromStopItem: StopItem, val toStopItem: StopItem) :
        SavedTripUiEvent
}
