package xyz.ksharma.krail.core.analytics.event

import xyz.ksharma.krail.core.analytics.AnalyticsScreen

sealed class AnalyticsEvent(val name: String, val properties: Map<String, Any>? = null) {

    data class ScreenViewEvent(val screen: AnalyticsScreen) : AnalyticsEvent(
        name = "view_screen",
        properties = mapOf("name" to screen.name)
    )

    // region SavedTrips

    data class SavedTripCardClickEvent(val fromStopId: String, val toStopId: String) :
        AnalyticsEvent(
            name = "saved_trip_card_click",
            properties = mapOf("fromStopId" to fromStopId, "toStopId" to toStopId),
        )

    data class DeleteSavedTripClickEvent(val fromStopId: String, val toStopId: String) :
        AnalyticsEvent(
            name = "delete_saved_trip_card_click",
            properties = mapOf("fromStopId" to fromStopId, "toStopId" to toStopId),
        )

    data object ReverseStopClickEvent : AnalyticsEvent(name = "reverse_stop_click")

    data class LoadTimeTableClickEvent(val fromStopId: String, val toStopId: String) :
        AnalyticsEvent(
            name = "load_timetable_click",
            properties = mapOf("fromStopId" to fromStopId, "toStopId" to toStopId),
        )

    data object SettingsClickEvent : AnalyticsEvent(name = "settings_click")

    // endregion

    // region Theme

    data class ThemeSelectedEvent(val transportMode: String) : AnalyticsEvent(
        name = "theme_selected",
        properties = mapOf("transportMode" to transportMode),
    )

    // endregion

    // region PlanTripScreen / DateTimeSelection Screen

    data object ResetTimeClickEvent : AnalyticsEvent("reset_time_click")

    // endregion

    // region TimeTable Screen

    data class ReverseTimeTableClickEvent(val fromStopId: String, val toStopId: String) :
        AnalyticsEvent(
            name = "reverse_time_table_click",
            properties = mapOf("fromStopId" to fromStopId, "toStopId" to toStopId)
        )

    data class SaveTripClickEvent(val fromStopId: String, val toStopId: String) :
        AnalyticsEvent(
            name = "save_trip_click",
            properties = mapOf("fromStopId" to fromStopId, "toStopId" to toStopId),
        )

    data class PlanTripClickEvent(val fromStopId: String, val toStopId: String) :
        AnalyticsEvent(
            name = "plan_trip_click",
            properties = mapOf("fromStopId" to fromStopId, "toStopId" to toStopId),
        )

    data class DateTimeSelectEvent(
        val dayOfWeek: String,
        val time: String,
        val journeyOption: String,
    ) : AnalyticsEvent(
        name = "date_time_select",
        properties = mapOf("dayOfWeek" to dayOfWeek, "time" to time, "option" to journeyOption),
    )

    data class JourneyCardExpandEvent(val hasStarted: Boolean) :
        AnalyticsEvent(
            name = "journey_card_expand",
            properties = mapOf("hasStarted" to hasStarted),
        )

    data class JourneyCardCollapseEvent(val hasStarted: Boolean) : AnalyticsEvent(
        name = "journey_card_collapse",
        properties = mapOf("hasStarted" to hasStarted),
    )

    data class JourneyLegClickEvent(val expanded: Boolean) : AnalyticsEvent(
        name = "journey_leg_click",
        properties = mapOf("expanded" to expanded),
    )

    data class JourneyAlertClickEvent(val fromStopId: String, val toStopId: String) :
        AnalyticsEvent(
            name = "journey_alert_click",
            properties = mapOf("fromStopId" to fromStopId, "toStopId" to toStopId),
        )

    // endregion

    // region Generic Events
    data class BackClickEvent(val fromScreen: String) :
        AnalyticsEvent(
            name = "back_click",
            properties = mapOf("fromScreen" to fromScreen),
        )

    data class AppStart(
        val deviceType: String,
        val appVersion: String,
        val osVersion: String,
        val deviceModel: String,
        val fontSize: String,
        val isDarkTheme: Boolean,
    ) : AnalyticsEvent(name = "app_start", properties = mapOf("app_start" to "app_start"))

    // endregion
}
