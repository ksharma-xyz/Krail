package xyz.ksharma.krail.coroutines.ext

import kotlinx.coroutines.ensureActive
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.coroutineContext

/**
 * An extension to  [kotlin.runCatching].
 *
 * Calls the specified function block with this value as its receiver and returns its encapsulated
 * result if invocation was successful, catching any [Throwable] exception that was thrown from the
 * block function execution and encapsulating it as a failure.
 *
 * Will not catch [CancellationException].
 */
suspend inline fun <T, R> T.safeResult(block: T.() -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: Throwable) {
        // Should not catch CancellationException
        coroutineContext.ensureActive()
        Result.failure(e)
    }
}
