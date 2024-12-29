package xyz.ksharma.krail.trip.planner.network.api.service

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import xyz.ksharma.krail.trip.planner.network.BuildKonfig

actual fun httpClient(): HttpClient {
    return HttpClient(Darwin) {
        expectSuccess = true
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }
        install(Logging) {
//            if(debug) - TODO
//            level = LogLevel.BODY
        }

        defaultRequest {
            headers.append(HttpHeaders.Authorization, "apikey ${BuildKonfig.IOS_NSW_TRANSPORT_API_KEY}")
        }
    }
}
