package xyz.ksharma.krail.network

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import xyz.ksharma.krail.network.di.NetworkModule.Companion.BASE_URL
import xyz.ksharma.krail.network.files.toPath
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.zip.ZipInputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GtfsServiceImpl @Inject constructor(
    private val okHttpClient: OkHttpClient,
    @ApplicationContext private val context: Context,
) : GtfsService {

    private val TAG = "SydneyTrainsServiceImpl"

    override suspend fun getSydneyTrainSchedule(): Response {
        val request = Request.Builder()
            .url("$BASE_URL/v1/gtfs/schedule/sydneytrains") // Replace with your API endpoint
            .build()

        val response = okHttpClient.newCall(request).execute()
        // don't log it's entire response body,which is huge.
        // Log.d(TAG, "fetchSydneyTrains: ${response.body?.string()}")

        val map = getHTMLZipOk(response)
        Log.d(TAG, "filesMap: $map")

        return response
    }

    @Throws(IOException::class)
    fun getHTMLZipOk(response: Response) {
        if (!response.isSuccessful) {
            throw IOException("Unexpected code ${response.code}")
        }

        val responseBody = response.body!!

        ZipInputStream(responseBody.byteStream()).use { inputStream ->
            // List files in zip
            var zipEntry = inputStream.nextEntry

            while (zipEntry != null) {
                val isDirectory = zipEntry.name.endsWith(File.separator)
                val path: Path = context.toPath(zipEntry.name)

                Log.d(TAG, "zipEntry: $zipEntry")

                writeToCacheFromZip(isDirectory, path, inputStream)

                zipEntry = inputStream.nextEntry
            }
            inputStream.closeEntry()
        }
        response.close()
    }

    /**
     * Extract files from zip and save to a file in cache directory.
     */
    private fun writeToCacheFromZip(
        isDirectory: Boolean,
        path: Path,
        inputStream: ZipInputStream
    ) {
        if (isDirectory) {
            Files.createDirectories(path)
        } else {
            // Handle creation of parent directories
            if (path.parent != null && Files.notExists(path.parent)) {
                Files.createDirectories(path.parent)
            }
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING)
        }
    }
}
