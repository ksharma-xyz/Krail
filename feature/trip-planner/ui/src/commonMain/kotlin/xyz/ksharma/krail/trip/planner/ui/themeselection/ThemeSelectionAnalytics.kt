package xyz.ksharma.krail.trip.planner.ui.themeselection

import xyz.ksharma.krail.core.analytics.Analytics
import xyz.ksharma.krail.core.analytics.event.AnalyticsEvent
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode

internal fun Analytics.trackThemeSelectionEvent(productClass: Int) {
    productClass.let { TransportMode.toTransportModeType(it) }?.let {
        track(AnalyticsEvent.ThemeSelectedEvent(transportMode = it.name))
    } ?: run {
        track(AnalyticsEvent.ThemeSelectedEvent(transportMode = productClass.toString()))
    }
}
