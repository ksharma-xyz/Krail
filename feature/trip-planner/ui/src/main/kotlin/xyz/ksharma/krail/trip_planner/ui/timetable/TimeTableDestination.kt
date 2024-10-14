package xyz.ksharma.krail.trip_planner.ui.timetable

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import xyz.ksharma.krail.trip_planner.ui.navigation.TimeTableRoute
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableUiEvent

internal fun NavGraphBuilder.timeTableDestination(navController: NavHostController) {
    composable<TimeTableRoute> { backStackEntry ->
        val viewModel = hiltViewModel<TimeTableViewModel>()
        val timeTableState by viewModel.uiState.collectAsStateWithLifecycle()
        val route: TimeTableRoute = backStackEntry.toRoute()
        val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
        if (isLoading) {
            viewModel.onEvent(TimeTableUiEvent.LoadTimeTable(route.fromStopId, route.toStopId))
        }
        // Subscribe to the isActive state flow - for updating the TimeText periodically.
        val isActive by viewModel.isActive.collectAsStateWithLifecycle()

        TimeTableScreen(timeTableState)
    }
}
