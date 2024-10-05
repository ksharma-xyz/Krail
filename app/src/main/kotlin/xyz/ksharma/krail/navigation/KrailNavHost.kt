package xyz.ksharma.krail.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip_planner.ui.navigation.SavedTripsRoute
import xyz.ksharma.krail.trip_planner.ui.navigation.tripPlannerDestinations

@Composable
fun KrailNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = KrailNavRoute1,
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
    ) {
        tripPlannerDestinations()

        composable<KrailNavRoute1> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = KrailTheme.colors.background)
            ) {
                // Add your composable here
                Text(text = "Click", modifier = Modifier.clickable {
                    navController.navigate(SavedTripsRoute)
                })
            }
        }
    }
}

@Serializable
data object KrailNavRoute1
