package xyz.ksharma.krail.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import xyz.ksharma.krail.network.BuildConfig
import xyz.ksharma.krail.network.interceptor.AuthInterceptor

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okhttpBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            okhttpBuilder.addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
        }
        okhttpBuilder.addInterceptor(AuthInterceptor())
        return okhttpBuilder.build()
    }

    companion object {
        const val BASE_URL = "https://api.transport.nsw.gov.au"
    }
}
