package xyz.ksharma.krail.network

import okhttp3.OkHttpClient
import okhttp3.Request
import xyz.ksharma.krail.network.di.NetworkModule.Companion.BASE_URL
import java.io.IOException
import javax.inject.Inject

class SydneyTrainsServiceImpl(@Inject val okHttpClient: OkHttpClient) : SydneyTrainsService {

    override fun fetchSydneyTrains(): ByteArray {
        val request = Request.Builder()
            .url(BASE_URL + "v1/gtfs/schedule/sydneytrains") // Replace with your API endpoint
            .build()

        val response = okHttpClient.newCall(request).execute()
        return response.body?.bytes() ?: throw IOException("Failed to fetchSydneyTrains data")
    }
}
