package xyz.ksharma.krail.core.analytics.event

import xyz.ksharma.krail.core.analytics.AnalyticsScreen

class SavedTripEvent(
    override var componentName: String?,
    override var action: AnalyticsAction,
) : AnalyticsEvent {

    override val screen: AnalyticsScreen
        get() = AnalyticsScreen.SavedTrip

    companion object {
        object Component {
            const val SAVED_TRIP_BUTTON = "SavedTripButton"
        }

        object Property {
            const val fromStopId = "fromStopId"
            const val toStopId = "toStopId"
            const val saved = "saved"
        }
    }
}
