package xyz.ksharma.krail.trip_planner.ui.navigation

import androidx.compose.runtime.LaunchedEffect
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
import xyz.ksharma.krail.trip_planner.ui.savedtrips.SavedTripsScreen
import xyz.ksharma.krail.trip_planner.ui.savedtrips.SavedTripsViewModel
import xyz.ksharma.krail.trip_planner.ui.searchstop.SearchStopScreen
import xyz.ksharma.krail.trip_planner.ui.searchstop.SearchStopViewModel
import xyz.ksharma.krail.trip_planner.ui.state.searchstop.model.StopItem
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
        composable<SavedTripsRoute> { backStackEntry ->
            val viewModel = hiltViewModel<SavedTripsViewModel>()
            val savedTripState by viewModel.uiState.collectAsStateWithLifecycle()

            val fromStopItem =
                backStackEntry.savedStateHandle.get<StopItem>(SearchStopFieldType.FROM.key)
            val toStopItem =
                backStackEntry.savedStateHandle.get<StopItem>(SearchStopFieldType.TO.key)

            LaunchedEffect(fromStopItem) {
                Timber.d("fromStopItem: $fromStopItem")
            }

            LaunchedEffect(toStopItem) {
                Timber.d("toStopItem: $toStopItem")
            }

            SavedTripsScreen(
                savedTripsState = savedTripState,
                fromStopItem = fromStopItem,
                toStopItem = toStopItem,
                fromButtonClick = {
                    Timber.d("fromButtonClick - nav: ${SearchStopRoute(fieldType = SearchStopFieldType.FROM)}")
                    navController.navigate(SearchStopRoute(fieldType = SearchStopFieldType.FROM))
                },
                toButtonClick = {
                    Timber.d("toButtonClick - nav: ${SearchStopRoute(fieldType = SearchStopFieldType.TO)}")
                    navController.navigate(SearchStopRoute(fieldType = SearchStopFieldType.TO))
                },
                onSearchButtonClick = {

                },
            ) { event ->
                viewModel.onEvent(event)
            }
        }

        composable<TimeTableRoute> {
            val viewModel = hiltViewModel<TimeTableViewModel>()
            val timeTableState by viewModel.uiState.collectAsStateWithLifecycle()

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
                searchStopState = searchStopState,
                onStopSelected = { stopItem ->
                    Timber.d("onStopSelected: fieldTypeKey=${route.fieldType.key} and stopItem: $stopItem")
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        route.fieldType.key,
                        stopItem
                    )
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
data object TimeTableRoute

@Serializable
data class SearchStopRoute(val fieldType: SearchStopFieldType)
