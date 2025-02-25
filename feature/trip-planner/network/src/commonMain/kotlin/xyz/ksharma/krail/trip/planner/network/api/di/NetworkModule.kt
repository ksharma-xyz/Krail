package xyz.ksharma.krail.trip.planner.network.api.di

import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import xyz.ksharma.krail.core.di.DispatchersComponent.Companion.IODispatcher
import xyz.ksharma.krail.trip.planner.network.api.ratelimit.NetworkRateLimiter
import xyz.ksharma.krail.trip.planner.network.api.ratelimit.RateLimiter
import xyz.ksharma.krail.trip.planner.network.api.service.RealTripPlanningService
import xyz.ksharma.krail.trip.planner.network.api.service.TripPlanningService
import xyz.ksharma.krail.trip.planner.network.api.service.httpClient

val networkModule = module {
    singleOf(::NetworkRateLimiter) { bind<RateLimiter>() }
    single<HttpClient> {
        httpClient(
            appInfoProvider = get(),
            coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default),
        )
    }

    single {
        RealTripPlanningService(
            httpClient = get(),
            ioDispatcher = get(named(IODispatcher)),
        )
    } bind TripPlanningService::class
}
