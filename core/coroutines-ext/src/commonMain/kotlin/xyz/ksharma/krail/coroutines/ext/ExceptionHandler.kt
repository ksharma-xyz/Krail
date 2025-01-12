package xyz.ksharma.krail.coroutines.ext

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import xyz.ksharma.krail.core.log.logError

inline fun <reified T> CoroutineScope.launchWithExceptionHandler(
    dispatcher: CoroutineDispatcher,
    crossinline block: suspend CoroutineScope.() -> Unit,
) = launch(context = dispatcher + coroutineExceptionHandler(message = T::class.simpleName)) {
    block()
}

fun coroutineExceptionHandler(message: String? = null) =
    CoroutineExceptionHandler { _, throwable ->
        logError(message = message ?: throwable.message ?: "", throwable = throwable)
    }
