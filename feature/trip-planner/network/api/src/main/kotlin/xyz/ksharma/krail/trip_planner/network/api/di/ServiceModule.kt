package xyz.ksharma.krail.trip_planner.network.api.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import xyz.ksharma.krail.trip_planner.network.api.service.TripPlanningService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideTripPlanningService(retrofit: Retrofit): TripPlanningService {
        return retrofit.create(TripPlanningService::class.java)
    }
}
