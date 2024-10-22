package xyz.ksharma.krail.trip.planner.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import xyz.ksharma.krail.trip.planner.ui.savedtrips.savedTripsDestination
import xyz.ksharma.krail.trip.planner.ui.searchstop.searchStopDestination
import xyz.ksharma.krail.trip.planner.ui.timetable.timeTableDestination

/**
 * Nested navigation graph for the trip planner feature.
 * It contains all the screens in the feature Trip Planner.
 */
fun NavGraphBuilder.tripPlannerDestinations(
    navController: NavHostController, // TODO -  do not wanna add NavController here, but moving all callbacks to app module is not scaleable.
) {
    navigation<TripPlannerNavRoute>(startDestination = SavedTripsRoute) {
        savedTripsDestination(navController)

        searchStopDestination(navController)

        timeTableDestination()
    }
}

internal enum class SearchStopFieldType(val key: String) {
    FROM(key = "FromSearchStopResult"),
    TO(key = "ToSearchStopResult"),
}

@Serializable
internal data object TripPlannerNavRoute

@Serializable
data object SavedTripsRoute

@Serializable
internal data class TimeTableRoute(
    val fromStopId: String,
    val fromStopName: String,
    val toStopId: String,
    val toStopName: String,
)

@Serializable
internal data class SearchStopRoute(val fieldType: SearchStopFieldType)
