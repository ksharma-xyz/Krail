package xyz.ksharma.krail.trip_planner.ui.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import timber.log.Timber
import xyz.ksharma.krail.trip_planner.ui.savedtrips.savedTripsDestination
import xyz.ksharma.krail.trip_planner.ui.searchstop.SearchStopScreen
import xyz.ksharma.krail.trip_planner.ui.searchstop.SearchStopViewModel
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableUiEvent
import xyz.ksharma.krail.trip_planner.ui.timetable.TimeTableScreen
import xyz.ksharma.krail.trip_planner.ui.timetable.TimeTableViewModel

/**
 * Nested navigation graph for the trip planner feature.
 * It contains all the screens in the feature Trip Planner.
 */
fun NavGraphBuilder.tripPlannerDestinations(
    navController: NavHostController, // TODO -  do not wanna add NavController here, but moving all callbacks to app module is not scaleable.
) {
    navigation<TripPlannerNavRoute>(startDestination = SavedTripsRoute) {
        savedTripsDestination(navController)

        composable<TimeTableRoute> { backStackEntry ->
            val viewModel = hiltViewModel<TimeTableViewModel>()
            val timeTableState by viewModel.uiState.collectAsStateWithLifecycle()
            val route: TimeTableRoute = backStackEntry.toRoute()
            val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
            if (isLoading) {
                viewModel.onEvent(TimeTableUiEvent.LoadTimeTable(route.fromStopId, route.toStopId))
            }

            TimeTableScreen(timeTableState) { event ->
                viewModel.onEvent(event)
            }
        }

        composable<SearchStopRoute> { backStackEntry ->
            val viewModel = hiltViewModel<SearchStopViewModel>()
            val searchStopState by viewModel.uiState.collectAsStateWithLifecycle()
            val route: SearchStopRoute = backStackEntry.toRoute()
            Timber.d("SearchStopRoute: $route")

            SearchStopScreen(
                key = route.fieldType.key,
                searchStopState = searchStopState,
                onStopSelected = { stopItem ->
                    Timber.d("onStopSelected: fieldTypeKey=${route.fieldType.key} and stopItem: $stopItem")
/*
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        route.fieldType.key,
                        stopItem.toJsonString(),
                    )
*/
                    navController.popBackStack()
                }) { event -> viewModel.onEvent(event) }
        }
    }
}


enum class SearchStopFieldType(val key: String) {
    FROM(key = "FromSearchStopResult"),
    TO(key = "ToSearchStopResult")
}

@Serializable
data object TripPlannerNavRoute

@Serializable
data object SavedTripsRoute

@Serializable
data class TimeTableRoute(
    val fromStopId: String,
    val fromStopName: String,
    val toStopId: String,
    val toStopName: String,
)

@Serializable
data class SearchStopRoute(val fieldType: SearchStopFieldType)
