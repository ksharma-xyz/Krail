package xyz.ksharma.krail.trip.planner.ui.components

enum class JourneyCardState {
    /**
     * Default state of the card. as displayed in the list.
     */
    DEFAULT,

    /**
     * Card displaying information about different legs of the journey.
     * The number of stops and duration of each leg.
     */
    COLLAPSED,

    /**
     * Card displaying the full journey details of a certain leg.
     * It will display all the stops in the leg, with platform and timing information.
     */
    EXPANDED,
}
