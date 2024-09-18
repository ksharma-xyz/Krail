package xyz.ksharma.krail.trip_planner.network.real.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.ksharma.krail.trip_planner.network.api.repository.TripPlanningRepository
import xyz.ksharma.krail.trip_planner.network.real.repository.RealTripPlanningRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TripPlanningModule {

    @Binds
    @Singleton
    abstract fun bindTripPlanningRepository(impl: RealTripPlanningRepository): TripPlanningRepository
}
