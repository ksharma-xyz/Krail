package xyz.ksharma.krail.common

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import xyz.ksharma.krail.sandook.createSandookComponent
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.trip.planner.network.api.createNetworkComponent

@Composable
fun KrailApp() {

    LaunchedEffect(Unit) {
        val httpClient = createNetworkComponent().provideHttpClient()
        println("HTTP Client: ${httpClient.engine.config}")

        val sandookComponent = createSandookComponent()
        sandookComponent.sandookDb.insertOrReplaceTheme(1)
        println("Sandook component: ${sandookComponent.sandookDb}")
    }

    KrailTheme {
        Text("Hello, Krail!")
//        KrailNavHost()
    }
}
