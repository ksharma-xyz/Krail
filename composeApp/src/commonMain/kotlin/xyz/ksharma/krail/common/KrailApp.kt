package xyz.ksharma.krail.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.russhwolf.settings.Settings
import kotlinx.coroutines.delay
import xyz.ksharma.krail.sandook.RealSandook
import xyz.ksharma.krail.taj.components.Text
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.trip.planner.network.api.model.StopType
import xyz.ksharma.krail.trip.planner.network.api.service.rememberHttpClient
import xyz.ksharma.krail.trip.planner.network.api.service.stop_finder.fetchStop
import xyz.ksharma.krail.trip.planner.network.api.service.trip.fetchTrip

@Composable
fun KrailApp() {
    KrailTheme {

/*
        LaunchedEffect(Unit) {
            val sandook = RealSandook(Settings())
            println("sandook: " + sandook)
            sandook.putString("key", "value")

            delay(2000)
            val sandook1 = RealSandook(Settings())
            println("sandook1: " + sandook1)
            var x = sandook1.getString("key")
            println("Sandook1 value: x: $x")
            x = sandook.getString("key")
            println("Sandook value: x: $x")
        }
*/

        val httpClient = rememberHttpClient()
        LaunchedEffect(Unit) {
            try {

                val stopResponse = fetchStop(
                    httpClient = httpClient,
                    stopType = StopType.STOP,
                    stopSearchQuery = "central"
                )
                println("stopResponse total: ${stopResponse.locations?.size}")

                stopResponse.locations?.forEach {
                    println("Stop: ${it.name}, ${it.id}")
                }
            } catch (e: Exception) {
                println("Stop Exception: $e")
                throw e
            }
        }


        Column(modifier = Modifier.fillMaxSize().background(color = KrailTheme.colors.surface)) {
            Text(
                "Hello, Krail!",
                style = KrailTheme.typography.titleLarge,
                modifier = Modifier.statusBarsPadding()
            )
        }
    }
}
