package xyz.ksharma.krail.trip.planner.network.api.service

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun getHttpClient(): HttpClient {
    return HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }
        install(Logging) {
//            if(debug) - TODO
            level = LogLevel.BODY
        }
    }
}

@Composable
fun rememberHttpClient() = remember { getHttpClient() }

internal const val NSW_TRANSPORT_BASE_URL = "https://api.transport.nsw.gov.au"
