package xyz.ksharma.krail.trip_planner.ui.state.savedtrip

import xyz.ksharma.krail.trip_planner.ui.state.searchstop.model.StopItem

sealed interface SavedTripUiEvent {
    data object LoadSavedTrips : SavedTripUiEvent
    data object ReverseButtonClicked : SavedTripUiEvent
    data class SavedTripClicked(val savedTrip: String) : SavedTripUiEvent
    data class DeleteSavedTrip(val savedTrip: String) : SavedTripUiEvent
    class FromStopFieldUpdated(val fromStopItem: StopItem) : SavedTripUiEvent
    class ToStopFieldUpdated(val toStopItem: StopItem) : SavedTripUiEvent
}
