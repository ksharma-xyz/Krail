package xyz.ksharma.krail.sydney.trains.database.real.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.ksharma.krail.database.sydney.trains.database.api.SydneyTrainsStaticDB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SydneyTrainsDbModule {

    @Binds
    @Singleton
    abstract fun bindSydneyTrainsStaticDB(impl: RealSydneyTrainsStaticDb): SydneyTrainsStaticDB
}
