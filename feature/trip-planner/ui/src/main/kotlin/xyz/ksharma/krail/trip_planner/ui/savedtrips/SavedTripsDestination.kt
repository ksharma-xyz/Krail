package xyz.ksharma.krail.trip_planner.ui.savedtrips

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import xyz.ksharma.krail.trip_planner.ui.state.searchstop.model.StopItem
import xyz.ksharma.krail.trip_planner.ui.state.searchstop.model.StopItem.Companion.fromJsonString

internal fun NavGraphBuilder.savedTripsDestination(navController: NavHostController) {
    composable<SavedTripsRoute> { backStackEntry ->
        val viewModel = hiltViewModel<SavedTripsViewModel>()
        val savedTripState by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            val fromStopItem: StopItem? =
                backStackEntry.savedStateHandle.get<String>(SearchStopFieldType.FROM.key)
                    ?.let { fromJsonString(it) }
            val toStopItem: StopItem? =
                backStackEntry.savedStateHandle.get<String>(SearchStopFieldType.TO.key)
                    ?.let { fromJsonString(it) }

            if (fromStopItem != null) viewModel.onEvent(
                SavedTripUiEvent.FromStopFieldUpdated(fromStopItem)
            )

            if (toStopItem != null) viewModel.onEvent(
                SavedTripUiEvent.ToStopFieldUpdated(
                    toStopItem
                )
            )
        }

        SavedTripsScreen(
            savedTripsState = savedTripState,
            fromButtonClick = {
                Timber.d("fromButtonClick - nav: ${SearchStopRoute(fieldType = SearchStopFieldType.FROM)}")
                navController.navigate(SearchStopRoute(fieldType = SearchStopFieldType.FROM))
            },
            toButtonClick = {
                Timber.d("toButtonClick - nav: ${SearchStopRoute(fieldType = SearchStopFieldType.TO)}")
                navController.navigate(SearchStopRoute(fieldType = SearchStopFieldType.TO))
            },
            onReverseButtonClick = { fromStop, toStop ->
                backStackEntry.savedStateHandle[SearchStopFieldType.FROM.key] = fromStop?.toJsonString()
                backStackEntry.savedStateHandle[SearchStopFieldType.TO.key] = toStop?.toJsonString()
                viewModel.onEvent(SavedTripUiEvent.ReverseButtonClicked)
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
