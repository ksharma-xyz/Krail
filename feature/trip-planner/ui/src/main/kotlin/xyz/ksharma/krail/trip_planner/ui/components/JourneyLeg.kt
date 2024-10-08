package xyz.ksharma.krail.trip_planner.ui.components

import androidx.compose.ui.graphics.Color

data class JourneyLeg(

    val transportation: Transportation,

    /**
     * Vehicle Headline
     * Train - City Circle via Parramatta
     * **/
    val headline: String,

    /**
     * List of Stops with Departure Time for the Journey.
     */
    val stops: List<Stop>,

    /**
     * 4 Stops (12 min) etc.
     */
    val summary: String,
) {
    data class Transportation(
        val backgroundColor: Color,
        val letter: Char,
        val badgeText: String,
    )
}
