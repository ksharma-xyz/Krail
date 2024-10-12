package xyz.ksharma.krail.trip_planner.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import xyz.ksharma.krail.trip_planner.ui.state.searchstop.model.StopItem.Companion.fromJsonString
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
        composable<SavedTripsRoute> { backStackEntry ->
            val viewModel = hiltViewModel<SavedTripsViewModel>()
            val savedTripState by viewModel.uiState.collectAsStateWithLifecycle()

            val fromArg = backStackEntry.savedStateHandle.get<String>(SearchStopFieldType.FROM.key)
                ?.let { fromJsonString(it) }
            val toArg = backStackEntry.savedStateHandle.get<String>(SearchStopFieldType.TO.key)
                ?.let { fromJsonString(it) }

            var fromStopItem: StopItem? by rememberSaveable { mutableStateOf(fromArg) }
            var toStopItem: StopItem? by rememberSaveable { mutableStateOf(toArg) }

            LaunchedEffect(fromArg) {
                fromStopItem = fromArg
                Timber.d("Change fromStopItem: $fromStopItem")
            }

            LaunchedEffect(toArg) {
                toStopItem = toArg
                Timber.d("Change toStopItem: $toStopItem")
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
                onReverseButtonClick = {
                    Timber.d("onReverseButtonClick:")
                    val bufferStop = fromStopItem
                    backStackEntry.savedStateHandle[SearchStopFieldType.FROM.key] =
                        toStopItem?.toJsonString()
                    backStackEntry.savedStateHandle[SearchStopFieldType.TO.key] =
                        bufferStop?.toJsonString()

                    fromStopItem = toStopItem
                    toStopItem = bufferStop

                },
                onSearchButtonClick = {
                    if (fromStopItem != null && toStopItem != null) {
                        navController.navigate(
                            TimeTableRoute(
                                fromStopId = fromStopItem?.stopId!!,
                                fromStopName = fromStopItem?.stopName!!,
                                toStopId = toStopItem?.stopId!!,
                                toStopName = toStopItem?.stopName!!,
                            )
                        )
                    } else {
                        // TODO - show message - to select both stops
                        Timber.e("Select both stops")
                    }
                },
            ) { event ->
                viewModel.onEvent(event)
            }
        }

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

            SearchStopScreen(
                searchStopState = searchStopState,
                onStopSelected = { stopItem ->
                    Timber.d("onStopSelected: fieldTypeKey=${route.fieldType.key} and stopItem: $stopItem")

                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        route.fieldType.key,
                        stopItem.toJsonString(),
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
data class TimeTableRoute(
    val fromStopId: String,
    val fromStopName: String,
    val toStopId: String,
    val toStopName: String,
)

@Serializable
data class SearchStopRoute(val fieldType: SearchStopFieldType)
