package xyz.ksharma.krail.trip.planner.network.api

import io.ktor.client.HttpClient
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.KmpComponentCreate
import me.tatarka.inject.annotations.Provides
import xyz.ksharma.krail.trip.planner.network.api.ratelimit.NetworkRateLimiter
import xyz.ksharma.krail.trip.planner.network.api.ratelimit.RateLimiter
import xyz.ksharma.krail.trip.planner.network.api.service.RealTripPlanningService
import xyz.ksharma.krail.trip.planner.network.api.service.TripPlanningService
import xyz.ksharma.krail.trip.planner.network.api.service.httpClient

@Component
abstract class NetworkComponent {

    @Provides
    fun provideHttpClient(): HttpClient = httpClient()

    protected val RealTripPlanningService.bind: TripPlanningService
        @Provides get() = this

    protected val NetworkRateLimiter.bind: RateLimiter
        @Provides get() = this
}

@KmpComponentCreate
expect fun createNetworkComponent(): NetworkComponent
