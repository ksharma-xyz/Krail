package xyz.ksharma.krail.database.sydney.trains.database.api

import java.io.IOException
import okhttp3.Response

interface ZipEntryCacheManager {


    /**
     * Caches the content of a successful ZIP response in the cache directory associated with the
     * provided context. This function operates on a specified coroutine dispatcher for asynchronous
     * execution.
     *
     * @throws IOException If an I/O error occurs during the caching process,
     *         or if the response code is unexpected.
     * @param dispatcher The coroutine dispatcher to use for suspending operations.
     * @param context The context that provides the cache directory path.
     */
    suspend fun cacheZipResponse(response: Response)
}
