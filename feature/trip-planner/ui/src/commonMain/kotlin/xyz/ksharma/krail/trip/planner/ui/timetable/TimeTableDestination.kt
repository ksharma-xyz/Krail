package xyz.ksharma.krail.trip.planner.ui.timetable

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.koin.compose.viewmodel.koinViewModel
import xyz.ksharma.krail.trip.planner.ui.navigation.DateTimeSelectorRoute
import xyz.ksharma.krail.trip.planner.ui.navigation.ServiceAlertRoute
import xyz.ksharma.krail.trip.planner.ui.navigation.TimeTableRoute
import xyz.ksharma.krail.trip.planner.ui.state.timetable.TimeTableUiEvent
import xyz.ksharma.krail.trip.planner.ui.state.timetable.Trip

internal fun NavGraphBuilder.timeTableDestination(navController: NavHostController) {
    composable<TimeTableRoute> { backStackEntry ->
        val viewModel: TimeTableViewModel = koinViewModel<TimeTableViewModel>()
        val timeTableState by viewModel.uiState.collectAsStateWithLifecycle()
        val route: TimeTableRoute = backStackEntry.toRoute()
        val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
        if (isLoading) {
            viewModel.onEvent(TimeTableUiEvent.LoadTimeTable(trip = route.toTrip()))
        }
        // Subscribe to the isActive state flow - for updating the TimeText periodically.
        val isActive by viewModel.isActive.collectAsStateWithLifecycle()
        val expandedJourneyId: String? by viewModel.expandedJourneyId.collectAsStateWithLifecycle()

        // Arguments
        // Cannot use 'rememberSaveable' here because DateTimeSelectionItem is not Parcelable.
        // But it's saved in backStackEntry.savedStateHandle as json, so it's able to
        // handle config changes properly.
        val dateTimeSelectionText: String =
            backStackEntry.savedStateHandle.get<String>(key = DateTimeSelectorRoute.DATE_TIME_TEXT_KEY)
                ?: "Leave: Now"

        // Lookout for new updates
        LaunchedEffect(dateTimeSelectionText) {
            println("Changed dateTimeSelectionItem: $dateTimeSelectionText")
        }

        TimeTableScreen(
            timeTableState = timeTableState,
            expandedJourneyId = expandedJourneyId,
            onEvent = { viewModel.onEvent(it) },
            onBackClick = { navController.popBackStack() },
            onAlertClick = { journeyId ->
                println("journeyId: $journeyId")
                viewModel.fetchAlertsForJourney(journeyId) { alerts ->
                    if (alerts.isNotEmpty()) {
                        navController.navigate(
                            route = ServiceAlertRoute(alertsJsonList = alerts.map { it.toJsonString() }),
                            navOptions = NavOptions.Builder().setLaunchSingleTop(singleTop = true)
                                .build(),
                        )
                    }
                }
            },
            dateTimeSelectionText = dateTimeSelectionText,
            dateTimeSelectorClicked = {
                navController.navigate(
                    route = DateTimeSelectorRoute(),
                    navOptions = NavOptions.Builder().setLaunchSingleTop(singleTop = true).build(),
                )
            },
        )
    }
}

private fun TimeTableRoute.toTrip() = Trip(
    fromStopId = fromStopId,
    fromStopName = fromStopName,
    toStopId = toStopId,
    toStopName = toStopName,
)
