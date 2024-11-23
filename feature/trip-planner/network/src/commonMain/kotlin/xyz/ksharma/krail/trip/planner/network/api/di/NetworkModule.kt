package xyz.ksharma.krail.trip.planner.network.api.di

import io.ktor.client.HttpClient
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.ksharma.krail.trip.planner.network.api.ratelimit.NetworkRateLimiter
import xyz.ksharma.krail.trip.planner.network.api.ratelimit.RateLimiter
import xyz.ksharma.krail.trip.planner.network.api.service.RealTripPlanningService
import xyz.ksharma.krail.trip.planner.network.api.service.TripPlanningService
import xyz.ksharma.krail.trip.planner.network.api.service.httpClient

val networkModule = module {
    singleOf(::NetworkRateLimiter) { bind<RateLimiter>() }
    single<HttpClient> { httpClient() }
    singleOf(::RealTripPlanningService) { bind<TripPlanningService>() }
}
