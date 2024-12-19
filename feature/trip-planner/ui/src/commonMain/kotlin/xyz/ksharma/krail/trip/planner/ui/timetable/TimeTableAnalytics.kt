package xyz.ksharma.krail.trip.planner.ui.timetable

import xyz.ksharma.krail.core.analytics.Analytics
import xyz.ksharma.krail.core.analytics.event.AnalyticsEvent

internal fun Analytics.trackJourneyCardExpandEvent(hasStarted: Boolean) {
    track(AnalyticsEvent.JourneyCardExpandEvent(hasStarted = hasStarted))
}

internal fun Analytics.trackJourneyCardCollapseEvent(hasStarted: Boolean) {
    track(AnalyticsEvent.JourneyCardCollapseEvent(hasStarted = hasStarted))
}
