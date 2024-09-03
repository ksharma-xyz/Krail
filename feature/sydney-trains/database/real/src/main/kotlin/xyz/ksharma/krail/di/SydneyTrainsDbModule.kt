package xyz.ksharma.krail.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.ksharma.krail.RealSydneyTrainsDb
import xyz.ksharma.krail.database.api.SydneyTrainsDB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SydneyTrainsDbModule {

    @Binds
    @Singleton
    abstract fun bindTrainsDb(impl: RealSydneyTrainsDb): SydneyTrainsDB
}
