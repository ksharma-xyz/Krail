package xyz.ksharma.krail.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import xyz.ksharma.krail.network.di.NetworkModule.Companion.BASE_URL
import java.io.ByteArrayInputStream
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

@Singleton
class SydneyTrainsServiceImpl @Inject constructor(
    private val okHttpClient: OkHttpClient
) : SydneyTrainsService {

    private val TAG = "SydneyTrainsServiceImpl"

    override suspend fun fetchSydneyTrains(): ByteArray {
        val request = Request.Builder()
            .url("$BASE_URL/v1/gtfs/schedule/sydneytrains") // Replace with your API endpoint
            .build()

        val response = okHttpClient.newCall(request).execute()
//        Log.d(TAG, "fetchSydneyTrains: ${response.body?.string()}")
        return response.body?.bytes() ?: throw IOException("Failed to fetchSydneyTrains data")
    }
}
