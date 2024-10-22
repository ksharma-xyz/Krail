package xyz.ksharma.krail.trip_planner.ui.savedtrips

import androidx.compose.runtime.LaunchedEffect
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
import xyz.ksharma.krail.trip_planner.ui.state.searchstop.model.StopItem
import xyz.ksharma.krail.trip_planner.ui.state.searchstop.model.StopItem.Companion.fromJsonString

internal fun NavGraphBuilder.savedTripsDestination(navController: NavHostController) {
    composable<SavedTripsRoute> { backStackEntry ->
        val viewModel = hiltViewModel<SavedTripsViewModel>()
        val savedTripState by viewModel.uiState.collectAsStateWithLifecycle()

        val fromArg: String? =
            backStackEntry.savedStateHandle.get<String>(SearchStopFieldType.FROM.key)
        val toArg: String? =
            backStackEntry.savedStateHandle.get<String>(SearchStopFieldType.TO.key)

        // Cannot use 'rememberSaveable' here because StopItem is not Parcelable.
        // But it's saved in backStackEntry.savedStateHandle as json, so it's able to
        // handle config changes properly.
        var fromStopItem: StopItem? by remember {
            mutableStateOf(fromArg?.let { fromJsonString(it) })
        }
        var toStopItem: StopItem? by remember { mutableStateOf(toArg?.let { fromJsonString(it) }) }

        LaunchedEffect(fromArg) {
            fromArg?.let { fromStopItem = fromJsonString(it) }
            Timber.d("Change fromStopItem: $fromStopItem")
        }

        LaunchedEffect(toArg) {
            toArg?.let { toStopItem = fromJsonString(it) }
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
                        ),
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
}
