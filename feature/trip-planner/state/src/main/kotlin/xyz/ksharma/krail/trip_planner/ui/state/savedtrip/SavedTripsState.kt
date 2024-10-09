package xyz.ksharma.krail.trip_planner.ui.state.savedtrip

import xyz.ksharma.krail.trip_planner.ui.state.searchstop.model.StopItem

data class SavedTripsState(val fromStopItem: StopItem? = null, val toStopItem: StopItem? = null)
