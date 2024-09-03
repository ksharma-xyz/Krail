package xyz.ksharma.krail.network

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.Response
import timber.log.Timber
import xyz.ksharma.krail.coroutines.ext.safeResult
import xyz.ksharma.krail.utils.toPath
import java.io.File
import java.io.IOException
import java.nio.file.Path
import java.util.zip.ZipInputStream

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
@Throws(IOException::class)
suspend fun Response.cacheZipResponse(dispatcher: CoroutineDispatcher, context: Context) =
    safeResult(dispatcher) {
        if (!isSuccessful) {
            throw IOException("Unexpected code $code")
        }

        val responseBody = body!!

        ZipInputStream(responseBody.byteStream()).use { inputStream ->
            // List files in zip
            var zipEntry = inputStream.nextEntry

            while (zipEntry != null) {
                val isDirectory = zipEntry.name.endsWith(File.separator)
                val path: Path = context.toPath(zipEntry.name)

                Timber.d("zipEntry: $zipEntry")

                inputStream.writeToCache(isDirectory, path)

                zipEntry = inputStream.nextEntry
            }
            inputStream.closeEntry()
        }
        close()
    }.getOrElse { error ->
        Timber.d("cacheZipResponse", error)
    }
