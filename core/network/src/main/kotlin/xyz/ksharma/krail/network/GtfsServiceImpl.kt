package xyz.ksharma.krail.network

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import xyz.ksharma.krail.network.di.NetworkModule.Companion.BASE_URL
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
//        Log.d(TAG, "fetchSydneyTrains: ${response.body?.string()}")

        val map = getHTMLZipOk(response)
        Log.d(TAG, "filesMap: $map")

        return response
    }

    @Throws(IOException::class)
    fun getHTMLZipOk(response: Response): MutableMap<String, ByteArray> {

        val filesMap = mutableMapOf<String, ByteArray>()

        if (!response.isSuccessful) {
            throw IOException("Unexpected code ${response.code}")
        }

        val responseBody = response.body!!

        ZipInputStream(responseBody.byteStream()).use { inputStream ->
            // List files in zip
            var zipEntry = inputStream.nextEntry

            while (zipEntry != null) {
                val isDirectory = zipEntry.name.endsWith(File.separator)
                val path: Path = context.cacheDir.toPath().resolve(zipEntry.name).normalize()

                Log.d(TAG, "zipEntry: $zipEntry")

                if (isDirectory) {
                    Files.createDirectories(path)
                } else {
                    // Handle creation of parent directories
                    if (path.parent != null && Files.notExists(path.parent)) {
                        Files.createDirectories(path.parent)
                    }

                    // Copy files using NIO
                    Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING)
                    filesMap[zipEntry.name] = path.toFile().readBytes()
                }

                zipEntry = inputStream.nextEntry
            }
            inputStream.closeEntry()
        }
        response.close()

        return filesMap
    }


    /*
        @Throws(IOException::class)
        fun unzipResponse(response: Response): Map<String, ByteArray> {
            if (!response.isSuccessful) {
                throw IOException("Unexpected code ${response.code}")
            }

            val responseBody = response.body!!
            val files = mutableMapOf<String, ByteArray>()

            ZipInputStream(responseBody.byteStream()).use { zis ->
                var zipEntry: ZipEntry?
                while ((zipEntry = zis.nextEntry) != null) {
                    val filePath = context.cacheDir.toPath().resolve(zipEntry.name).normalize()

                    if (zipEntry.isDirectory) {
                        Files.createDirectories(filePath)
                    } else {
                        val fileData = ByteArrayOutputStream().use { buffer ->
                            zis.copyTo(buffer)
                            buffer.toByteArray()
                        }
                        files[filePath.fileName.toString()] = fileData
                    }
                }
            }

            return files
        }
    */
}
