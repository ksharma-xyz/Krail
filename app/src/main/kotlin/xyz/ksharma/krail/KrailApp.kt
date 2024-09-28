package xyz.ksharma.krail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.BasicText
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.ksharma.krail.design.system.theme.KrailTheme

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
            Column {
                BasicText("Krail App", style = KrailTheme.typography.bodyLarge)
                BasicText("Krail App", style = KrailTheme.typography.bodyMedium)
                BasicText("Krail App", style = KrailTheme.typography.bodySmall)
            }
        }
    }
}
