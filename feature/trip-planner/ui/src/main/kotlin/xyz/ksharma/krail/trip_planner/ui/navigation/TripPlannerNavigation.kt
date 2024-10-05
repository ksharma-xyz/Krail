package xyz.ksharma.krail.trip_planner.ui.navigation

import androidx.navigation.NavController

fun NavController.navigateToSearchStopScreen() {
    navigate(SearchStopRoute)
}

fun NavController.navigateToTimeTableScreen() {
    navigate(TimeTableRoute)
}
