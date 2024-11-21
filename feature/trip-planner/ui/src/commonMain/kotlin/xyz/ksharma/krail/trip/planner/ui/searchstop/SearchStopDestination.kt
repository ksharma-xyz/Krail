package xyz.ksharma.krail.trip.planner.ui.searchstop

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.koin.compose.viewmodel.koinNavViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import xyz.ksharma.krail.trip.planner.ui.navigation.SearchStopRoute

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.searchStopDestination(navController: NavHostController) {
    composable<SearchStopRoute> { backStackEntry ->
        val viewModel: SearchStopViewModel = koinNavViewModel<SearchStopViewModel>()
        val searchStopState by viewModel.uiState.collectAsStateWithLifecycle()
        val route: SearchStopRoute = backStackEntry.toRoute()

        SearchStopScreen(
            searchStopState = searchStopState,
            onStopSelect = { stopItem ->
                //Timber.d("onStopSelected: fieldTypeKey=${route.fieldType.key} and stopItem: $stopItem")

                navController.previousBackStackEntry?.savedStateHandle?.set(
                    route.fieldType.key,
                    stopItem.toJsonString(),
                )
                navController.popBackStack()
            },
        ) { event -> viewModel.onEvent(event) }
    }
}
