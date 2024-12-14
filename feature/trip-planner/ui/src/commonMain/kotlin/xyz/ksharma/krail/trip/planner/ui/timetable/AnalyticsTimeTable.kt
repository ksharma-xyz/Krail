package xyz.ksharma.krail.trip.planner.ui.timetable

import xyz.ksharma.krail.core.analytics.Analytics
import xyz.ksharma.krail.core.analytics.event.AnalyticsAction
import xyz.ksharma.krail.core.analytics.event.SavedTripEvent
import xyz.ksharma.krail.core.analytics.event.SavedTripEvent.Companion.Component.SAVED_TRIP_BUTTON
import xyz.ksharma.krail.trip.planner.ui.state.timetable.Trip

internal fun Analytics.trackSavedTripButtonClickEvent(saved: Boolean, tripInfo: Trip?) {
    track(
        event = SavedTripEvent(
            componentName = SAVED_TRIP_BUTTON,
            action = AnalyticsAction.Click,
        ),
        properties = tripInfo?.let { trip ->
            mapOf(
                SavedTripEvent.Companion.Property.fromStopId to trip.fromStopId,
                SavedTripEvent.Companion.Property.toStopId to trip.toStopId,
                SavedTripEvent.Companion.Property.saved to saved,
            )
        } ?: emptyMap()
    )
}
