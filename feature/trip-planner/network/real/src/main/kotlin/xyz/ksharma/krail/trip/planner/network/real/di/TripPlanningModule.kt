package xyz.ksharma.krail.trip.planner.network.real.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.ksharma.krail.trip.planner.network.api.RateLimiter
import xyz.ksharma.krail.trip.planner.network.api.repository.TripPlanningRepository
import xyz.ksharma.krail.trip.planner.network.real.ratelimit.APIRateLimiter
import xyz.ksharma.krail.trip.planner.network.real.repository.RealTripPlanningRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TripPlanningModule {

    @Binds
    @Singleton
    abstract fun bindTripPlanningRepository(impl: RealTripPlanningRepository): TripPlanningRepository

    @Binds
    // This clas should not be Singleton. It should be created per use-case.
    abstract fun bindRateLimiter(impl: APIRateLimiter): RateLimiter
}
