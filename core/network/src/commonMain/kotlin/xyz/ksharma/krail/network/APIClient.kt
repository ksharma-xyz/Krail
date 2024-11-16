package xyz.ksharma.krail.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class ApiClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // To avoid crashes if the API sends extra fields
            })
        }
    }

    suspend fun getApiResponse(apiKey: String): ApiResponse {
        return client.get("https://api.example.com/data") {
            headers {
                append("Authorization", "apikey $apiKey") // Example of Authorization header
                append(HttpHeaders.Accept, "application/json") // Accept JSON response
            }

            url {
                parameters.append("key", "value")
                parameters.append("search", "query")
            }
        }.body()
    }
}

@Serializable
data class ApiResponse(
    val id: Int,
    val name: String,
    val details: String,
)
