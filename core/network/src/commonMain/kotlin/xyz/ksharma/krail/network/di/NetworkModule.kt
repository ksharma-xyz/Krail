package xyz.ksharma.krail.network.di

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import xyz.ksharma.krail.network.BuildConfig
import xyz.ksharma.krail.network.interceptor.AuthInterceptor

@Component
abstract class NetworkModule {

    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                })
            }
            if (BuildConfig.DEBUG) {
                install(Logging) {
                    level = LogLevel.BODY
                }
            }
            install(AuthInterceptor)
            engine {
                requestTimeout = 30000
                connectTimeout = 10000
            }
        }
    }

    companion object {
        const val BASE_URL = "https://api.transport.nsw.gov.au"
    }
}
