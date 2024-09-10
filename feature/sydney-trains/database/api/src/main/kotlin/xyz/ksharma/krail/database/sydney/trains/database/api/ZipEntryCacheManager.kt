package xyz.ksharma.krail.database.sydney.trains.database.api

import okhttp3.Response

interface ZipEntryCacheManager {


    /**
     * Caches the content of a successful ZIP response in the cache directory associated with the
     * provided context. This function operates on a specified coroutine dispatcher for asynchronous
     * execution.
     *
     * @param response - The Okhttp [Response] from API, which contains the zip data.
     *
     */
    suspend fun cacheZipResponse(response: Response)
}
