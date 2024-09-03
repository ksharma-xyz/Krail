package xyz.ksharma.krail.sydney.trains.network.real.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.ksharma.krail.sydney.trains.network.api.SydneyTrainsService
import xyz.ksharma.krail.sydney.trains.network.real.RealSydneyTrainsService

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun bindSydneyTrainsService(impl: RealSydneyTrainsService): SydneyTrainsService
}
