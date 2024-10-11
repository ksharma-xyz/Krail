package xyz.ksharma.krail.trip_planner.ui.savedtrips

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import timber.log.Timber
import xyz.ksharma.krail.trip_planner.ui.navigation.SavedTripsRoute
import xyz.ksharma.krail.trip_planner.ui.navigation.SearchStopFieldType
import xyz.ksharma.krail.trip_planner.ui.navigation.SearchStopRoute
import xyz.ksharma.krail.trip_planner.ui.navigation.TimeTableRoute
import xyz.ksharma.krail.trip_planner.ui.state.savedtrip.SavedTripUiEvent
import xyz.ksharma.krail.trip_planner.ui.state.searchstop.model.StopItem.Companion.fromJsonString

internal fun NavGraphBuilder.savedTripsDestination(navController: NavHostController) {
    composable<SavedTripsRoute> { backStackEntry ->

        val viewModel = hiltViewModel<SavedTripsViewModel>()
        val savedTripState by viewModel.uiState.collectAsStateWithLifecycle()

        var from by remember {
            mutableStateOf(
                backStackEntry.savedStateHandle.get<String>(SearchStopFieldType.FROM.key)
                    ?.let { fromJsonString(it) }
            )
        }
        var to by remember {
            mutableStateOf(
                backStackEntry.savedStateHandle.get<String>(SearchStopFieldType.TO.key)
                    ?.let { fromJsonString(it) }
            )
        }

        SavedTripsScreen(
            fromStopItem = from,
            toStopItem = to,
            //savedTripsState = savedTripState,
            fromButtonClick = {
                Timber.d("fromButtonClick - nav: ${SearchStopRoute(fieldType = SearchStopFieldType.FROM)}")
                navController.navigate(SearchStopRoute(fieldType = SearchStopFieldType.FROM))
            },
            toButtonClick = {
                Timber.d("toButtonClick - nav: ${SearchStopRoute(fieldType = SearchStopFieldType.TO)}")
                navController.navigate(SearchStopRoute(fieldType = SearchStopFieldType.TO))
            },
            onReverseButtonClick = { fromStop, toStop ->
                backStackEntry.savedStateHandle[SearchStopFieldType.FROM.key] =
                    fromStop?.toJsonString()
                backStackEntry.savedStateHandle[SearchStopFieldType.TO.key] = toStop?.toJsonString()

                from = toStop
                to = fromStop

                viewModel.onEvent(SavedTripUiEvent.ReverseButtonClicked)
                Timber.d("onReverseButtonClick - from: ${
                    backStackEntry.savedStateHandle.get<String>(SearchStopFieldType.FROM.key)
                        ?.let { fromJsonString(it) }
                }"
                )
                Timber.d("onReverseButtonClick - to: ${
                    backStackEntry.savedStateHandle.get<String>(SearchStopFieldType.TO.key)
                        ?.let { fromJsonString(it) }
                }"
                )

            },
            onSearchButtonClick = { fromStop, toStop ->
                Timber.d("onSearchButtonClick: fromStop=${fromStop.stopName}, toStop=${toStop.stopName}")

                navController.navigate(
                    TimeTableRoute(
                        fromStopId = fromStop.stopId,
                        fromStopName = fromStop.stopName,
                        toStopId = toStop.stopId,
                        toStopName = toStop.stopName,
                    )
                )
            },
        ) { event ->
            viewModel.onEvent(event)
        }
    }
}
