package xyz.ksharma.krail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
internal fun KrailApp(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.DemoPage.name,
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
    ) {
        composable(route = AppScreen.DemoPage.name) {
            Text("Krail App")
        }
    }
}
