package xyz.ksharma.krail.core.analytics.event

import xyz.ksharma.krail.core.analytics.AnalyticsScreen

sealed class AnalyticsEvent(val name: String, val properties: Map<String, Any>? = null) {

    data class ScreenViewEvent(val screen: AnalyticsScreen) : AnalyticsEvent(
        name = "screen_view",
        properties = mapOf("name" to screen.name)
    )

    data class SavedTripCardClickEvent(val fromStopId: Int, val toStopId: Int) : AnalyticsEvent(
        name = "saved_trip_card_click",
        properties = mapOf("fromStopId" to fromStopId, "toStopId" to toStopId),
    )

    data class ThemeSelectedEvent(val productClass: Int) : AnalyticsEvent(
        name = "theme_selected",
        properties = mapOf("productClass" to productClass),
    )

    data object ReverseStopClickEvent : AnalyticsEvent(name = "reverse_stop_click")

    data class LoadJourneyClickEvent(val fromStopId: Int, val toStopId: Int) :
        AnalyticsEvent(
            name = "load_journey_click",
            properties = mapOf("fromStopId" to fromStopId, "toStopId" to toStopId),
        )

    data class ReverseTimeTableClickEvent(val fromStopId: Int, val toStopId: Int) :
        AnalyticsEvent(
            name = "reverse_time_table_click",
            properties = mapOf("fromStopId" to fromStopId, "toStopId" to toStopId)
        )

    data class SaveTripClickEvent(val fromStopId: Int, val toStopId: Int) :
        AnalyticsEvent(
            name = "save_trip_click",
            properties = mapOf("fromStopId" to fromStopId, "toStopId" to toStopId),
        )

    data class PlanTripClickEvent(val fromStopId: Int, val toStopId: Int) :
        AnalyticsEvent(
            name = "plan_trip_click",
            properties = mapOf("fromStopId" to fromStopId, "toStopId" to toStopId),
        )

    data class DateTimeSelectEvent(val dayOfWeek: String, val time: String, val type: String) :
        AnalyticsEvent(
            name = "date_time_select",
            properties = mapOf("dayOfWeek" to dayOfWeek, "time" to time, "type" to type),
        )

    data object ResetTimeClickEvent : AnalyticsEvent("reset_time_click")

    data class JourneyCardExpandEvent(val hasStarted: Boolean) :
        AnalyticsEvent(
            name = "journey_card_expand",
            properties = mapOf("hasStarted" to hasStarted),
        )

    data object JourneyCardCollapseEvent : AnalyticsEvent(name = "journey_card_collapse")

    data object JourneyLegClickEvent : AnalyticsEvent(name = "journey_leg_click")

    data class JourneyAlertClickEvent(val fromStopId: Int, val toStopId: Int) :
        AnalyticsEvent(
            name = "journey_alert_click",
            properties = mapOf("fromStopId" to fromStopId, "toStopId" to toStopId),
        )

    data class BackClickEvent(val fromScreen: String) :
        AnalyticsEvent(
            name = "back_click",
            properties = mapOf("fromScreen" to fromScreen),
        )

    data object SettingsClickEvent : AnalyticsEvent(name = "settings_click")
}
