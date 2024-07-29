package xyz.ksharma.krail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.ksharma.feature1.DemoScreen
import xyz.ksharma.feature1.DemoUiState
import xyz.ksharma.feature1.DemoViewModel

@Composable
internal fun KrailApp(
    viewModel: DemoViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.DemoPage.name,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = AppScreen.DemoPage.name) {
            val localUiState: DemoUiState by viewModel.uiState.collectAsStateWithLifecycle()
            when (val uiState = localUiState) {
                DemoUiState.Loading -> Text(text = "Loading")

                is DemoUiState.Success -> {
                    DemoScreen(
                        modifier = Modifier.fillMaxSize(),
                        demoData = uiState.demoData,
                        onItemClick = { id ->
                            println("nav to : $id screen")
                        },
                    )
                }

                DemoUiState.Error -> Text(text = "Something went wrong")
            }
        }
    }
}
