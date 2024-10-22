package xyz.ksharma.krail.trip.planner.ui.searchstop

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import timber.log.Timber
import xyz.ksharma.krail.trip.planner.ui.navigation.SearchStopRoute

fun NavGraphBuilder.searchStopDestination(navController: NavHostController) {
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
            },
        ) { event -> viewModel.onEvent(event) }
    }
}
