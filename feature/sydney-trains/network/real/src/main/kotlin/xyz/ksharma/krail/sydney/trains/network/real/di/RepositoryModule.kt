package xyz.ksharma.krail.sydney.trains.network.real.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.ksharma.krail.sydney.trains.network.api.repository.SydneyTrainsRepository
import xyz.ksharma.krail.sydney.trains.network.real.repository.RealSydneyTrainsRepository

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindSydneyTrainsRepository(impl: RealSydneyTrainsRepository): SydneyTrainsRepository
}
