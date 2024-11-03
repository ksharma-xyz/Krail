package xyz.ksharma.krail.trip.planner.ui.state.usualride

sealed interface UsualRideEvent {
    data class TransportModeSelected(val productClass: Int) : UsualRideEvent
}
