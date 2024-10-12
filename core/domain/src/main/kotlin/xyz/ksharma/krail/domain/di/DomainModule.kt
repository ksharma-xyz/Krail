package xyz.ksharma.krail.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.ksharma.krail.domain.DemoUseCase
import xyz.ksharma.krail.domain.DemoUseCaseImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class DomainModule {

    @Binds
    abstract fun bindDemoUseCase(impl: DemoUseCaseImpl): DemoUseCase
}
