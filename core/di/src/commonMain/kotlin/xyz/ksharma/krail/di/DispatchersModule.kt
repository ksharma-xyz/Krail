package xyz.ksharma.krail.di

import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Inject
import me.tatarka.inject.annotations.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Component
abstract class DispatchersComponent {

    abstract val dispatchersModule: DispatchersModule

    @Provides
    fun defaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

@Inject
class DispatchersModule(
    @Dispatcher(AppDispatchers.Default) val defaultDispatcher: CoroutineDispatcher,
    @Dispatcher(AppDispatchers.IO) val ioDispatcher: CoroutineDispatcher
)
