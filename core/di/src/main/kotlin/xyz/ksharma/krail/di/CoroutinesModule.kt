package xyz.ksharma.krail.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {

    @Provides
    fun provideCoroutineScope(@Dispatcher(AppDispatchers.IO) ioDispatcher: CoroutineDispatcher): CoroutineScope =
        CoroutineScope(context = ioDispatcher + SupervisorJob())
}
