package xyz.ksharma.krail.sydney.trains.database.real.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.ksharma.krail.database.sydney.trains.database.api.StopTimesStore
import xyz.ksharma.krail.database.sydney.trains.database.api.ZipEntryCacheManager
import xyz.ksharma.krail.sydney.trains.database.real.RealStopTimesStore
import xyz.ksharma.krail.sydney.trains.database.real.RealZipCacheManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SydneyTrainsDbModule {

    @Binds
    @Singleton
    abstract fun bindSydneyTrainsStaticDB(impl: RealStopTimesStore): StopTimesStore

    @Binds
    @Singleton
    abstract fun bindZipEntryCacheManager(impl: RealZipCacheManager): ZipEntryCacheManager
}
