package xyz.ksharma.krail.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.Request
import xyz.ksharma.krail.network.di.NetworkModule.Companion.BASE_URL
import xyz.ksharma.krail.network.interceptor.AuthInterceptor.Companion.API_KEY
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.zip.ZipInputStream
import javax.inject.Inject
import java.nio.file.Path
import javax.inject.Singleton


@Singleton
class SydneyTrainsServiceImpl @Inject constructor(
    private val okHttpClient: OkHttpClient,
    @ApplicationContext private val context: Context,
) : SydneyTrainsService {

    private val TAG = "SydneyTrainsServiceImpl"

    override suspend fun fetchSydneyTrains(): ByteArray {
        val request = Request.Builder()
            .addHeader("accept", "application/x-google-protobuf")
            .addHeader("Authorization", "apikey $API_KEY")
            .url("$BASE_URL/v1/gtfs/schedule/sydneytrains") // Replace with your API endpoint
            .build()

        val response = okHttpClient.newCall(request).execute()
//        Log.d(TAG, "fetchSydneyTrains: ${response.body?.string()}")

        val x = getHTMLZip("$BASE_URL/v1/gtfs/schedule/sydneytrains")

        return response.body?.bytes() ?: throw IOException("Failed to fetchSydneyTrains data")
    }


    @Throws(IOException::class)
    fun getByteArray(url: String?): ByteArray {

        val con = URL(url).openConnection() as HttpURLConnection
        con.setRequestProperty("Authorization", "apikey $API_KEY")

        ByteArrayOutputStream().use { buffer ->
            con.inputStream.use { `is` ->
                var nRead: Int
                val data = ByteArray(16384)

                while ((`is`.read(data, 0, data.size).also { nRead = it }) != -1) {
                    buffer.write(data, 0, nRead)
                }
                return buffer.toByteArray()
            }
        }
    }

    /***
     * Save files to cache dir
     */
    @Throws(IOException::class)
    fun getHTMLZip(url: String?, targetDir: Path = context.cacheDir.toPath()) {

        val con = URL(url).openConnection() as HttpURLConnection
        con.setRequestProperty("Authorization", "apikey $API_KEY")

        ZipInputStream(con.inputStream).use { zis ->

            // list files in zip
            var zipEntry = zis.nextEntry

            while (zipEntry != null) {
                val isDirectory = zipEntry.name.endsWith(File.separator)

                val newPath: Path = targetDir.resolve(zipEntry.name).normalize()

                if (isDirectory) {
                    Files.createDirectories(newPath)
                } else {
                    // example 1.2
                    // some zip stored file path only, need create parent directories
                    // e.g data/folder/file.txt

                    if (newPath.getParent() != null) {
                        if (Files.notExists(newPath.getParent())) {
                            Files.createDirectories(newPath.getParent())
                        }
                    }

                    // copy files, nio
                    Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING)
                }

                zipEntry = zis.nextEntry
            }
            zis.closeEntry()
        }
    }

}
