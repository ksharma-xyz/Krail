package xyz.ksharma.krail.trip.planner.network.real.di

import kotlinx.coroutines.CoroutineDispatcher
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher
import xyz.ksharma.krail.trip.planner.network.api.RateLimiter
import xyz.ksharma.krail.trip.planner.network.api.repository.TripPlanningRepository
import xyz.ksharma.krail.trip.planner.network.real.ratelimit.APIRateLimiter
import xyz.ksharma.krail.trip.planner.network.real.repository.RealTripPlanningRepository

@Component
abstract class TripPlanningComponent {

    abstract val tripPlanningRepository: TripPlanningRepository

    abstract val rateLimiter: RateLimiter

    @Provides
    fun provideTripPlanningRepository(
        @Dispatcher(AppDispatchers.IO) ioDispatcher: CoroutineDispatcher,
    ): TripPlanningRepository {
        return RealTripPlanningRepository(ioDispatcher = ioDispatcher)
    }

    @Provides
    fun provideRateLimiter(): RateLimiter {
        return APIRateLimiter()
    }
}
