package xyz.ksharma.krail.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.ksharma.krail.RealSydneyTrainsStaticDb
import xyz.ksharma.krail.database.api.SydneyTrainsStaticDB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SydneyTrainsDbModule {

    @Binds
    @Singleton
    abstract fun bindSydneyTrainsStaticDB(impl: RealSydneyTrainsStaticDb): SydneyTrainsStaticDB
}
