package xyz.ksharma.krail.trip.planner.ui.state.savedtrip

import xyz.ksharma.krail.trip.planner.ui.state.timetable.Trip

sealed interface SavedTripUiEvent {
    data object LoadSavedTrips : SavedTripUiEvent
    data class DeleteSavedTrip(val trip: Trip) : SavedTripUiEvent
}
