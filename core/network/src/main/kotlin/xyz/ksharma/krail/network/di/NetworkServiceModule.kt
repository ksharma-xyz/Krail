package xyz.ksharma.krail.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.ksharma.krail.network.SydneyTrainsService
import xyz.ksharma.krail.network.SydneyTrainsServiceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkServiceModule {

    @Binds
    abstract fun bindNetworkService(impl: SydneyTrainsServiceImpl): SydneyTrainsService
}
