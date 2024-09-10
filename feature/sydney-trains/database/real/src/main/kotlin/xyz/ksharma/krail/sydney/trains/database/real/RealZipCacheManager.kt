package xyz.ksharma.krail.sydney.trains.database.real

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.Response
import timber.log.Timber
import xyz.ksharma.krail.coroutines.ext.safeResult
import xyz.ksharma.krail.database.sydney.trains.database.api.ZipEntryCacheManager
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher
import xyz.ksharma.krail.utils.toPath
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.zip.ZipInputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealZipCacheManager @Inject constructor(
    @Dispatcher(AppDispatchers.IO) val ioDispatcher: CoroutineDispatcher,
    @ApplicationContext val context: Context,
) : ZipEntryCacheManager {

    override suspend fun cacheZipResponse(response: Response) = safeResult(ioDispatcher) {
        if (!response.isSuccessful) {
            throw IOException("Unexpected code ${response.code}")
        }

        val responseBody = response.body!!

        ZipInputStream(responseBody.byteStream()).use { inputStream ->
            // List files in zip
            var zipEntry = inputStream.nextEntry
            Timber.d("zipEntry: $zipEntry")

            while (zipEntry != null) {
                val isDirectory = zipEntry.name.endsWith(File.separator)
                val path: Path = context.toPath(zipEntry.name)

                inputStream.writeToCache(isDirectory, path)

                zipEntry = inputStream.nextEntry
            }
            inputStream.closeEntry()
        }
        response.close()
    }.getOrElse { exception ->
        Timber.e("Error while trying to cacheZipResponse", exception)
    }

    /**
     * Extracts a ZIP entry to a specified cache path.
     *
     * If the entry is a directory, it creates the directory structure.
     * If the entry is a file, it copies the file contents to the cache path.
     *
     * **Note:** If the target file already exists, it will be overwritten.
     *
     * ZipInputStream - The input stream containing the ZIP entry data.
     *
     * @param isDirectory Indicates whether the entry is a directory.
     * @param path The target path in the cache directory.
     */ // TODO - can be reusable across modules?
    private fun ZipInputStream.writeToCache(
        isDirectory: Boolean,
        path: Path,
    ) {
        if (isDirectory) {
            Files.createDirectories(path)
        } else {
            // Handle creation of parent directories
            if (path.parent != null && Files.notExists(path.parent)) {
                Files.createDirectories(path.parent)
            }
            Files.copy(this, path, StandardCopyOption.REPLACE_EXISTING)
        }
    }
}
