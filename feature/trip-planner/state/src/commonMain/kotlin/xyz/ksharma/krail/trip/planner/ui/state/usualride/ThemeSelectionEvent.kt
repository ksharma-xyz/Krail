package xyz.ksharma.krail.trip.planner.ui.state.usualride

sealed interface ThemeSelectionEvent {
    data class TransportModeSelected(val productClass: Int) : ThemeSelectionEvent
}
