package xyz.ksharma.krail.di

import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Inject
import me.tatarka.inject.annotations.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Component
abstract class CoroutinesComponent {

    abstract val coroutinesModule: CoroutinesModule

    @Provides
    fun provideCoroutineScope(
        @Dispatcher(AppDispatchers.IO) ioDispatcher: CoroutineDispatcher,
    ): CoroutineScope = CoroutineScope(context = ioDispatcher + SupervisorJob())
}

@Inject
class CoroutinesModule(
    @Dispatcher(AppDispatchers.IO) val ioDispatcher: CoroutineDispatcher,
)
