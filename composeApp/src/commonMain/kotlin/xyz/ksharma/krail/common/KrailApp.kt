package xyz.ksharma.krail.common

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.trip.planner.network.api.createNetworkComponent

@Composable
fun KrailApp() {

    LaunchedEffect(Unit) {
        val httpClient = createNetworkComponent().provideHttpClient()
        println("HTTP Client: ${httpClient.engine.config}")
    }

    KrailTheme {
        Text("Hello, Krail!")
//        KrailNavHost()
    }
}
