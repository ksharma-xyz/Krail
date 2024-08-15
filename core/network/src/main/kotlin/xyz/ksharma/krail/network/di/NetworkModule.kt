package xyz.ksharma.krail.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import xyz.ksharma.krail.network.RealTimeService

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).build()
    }

    @Provides
    fun provideRealTimeService(retrofit: Retrofit): RealTimeService {
        return retrofit.create(RealTimeService::class.java)
    }

    companion object {
        const val BASE_URL = "https://api.transport.nsw.gov.au"
    }
}
