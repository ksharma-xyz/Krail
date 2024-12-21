package xyz.ksharma.krail.trip.planner.ui.state.datetimeselector

sealed interface DateTimeSelectorEvent {

    data object ResetDateTimeClick : DateTimeSelectorEvent
}
