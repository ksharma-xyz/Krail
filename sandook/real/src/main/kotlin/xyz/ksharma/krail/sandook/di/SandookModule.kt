package xyz.ksharma.krail.sandook.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.ksharma.krail.sandook.RealSandook
import xyz.ksharma.krail.sandook.Sandook
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SandookModule {

    @Binds
    @Singleton
    internal abstract fun bindSandook(impl: RealSandook): Sandook
}
