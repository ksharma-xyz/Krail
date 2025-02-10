package xyz.ksharma.krail.trip.planner.ui.themeselection

import xyz.ksharma.krail.core.analytics.Analytics
import xyz.ksharma.krail.core.analytics.event.AnalyticsEvent

internal fun Analytics.trackThemeSelectionEvent(themeId: Int) {
    track(AnalyticsEvent.ThemeSelectedEvent(themeId = themeId.toString()))
}
