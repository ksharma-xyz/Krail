package xyz.ksharma.krail.nav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun KrailNavHost(startScreen: Screen) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startScreen,
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
    ) {

    }
}
