package xyz.ksharma.krail.network

import android.content.ContentValues.TAG
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import xyz.ksharma.krail.network.di.NetworkModule.Companion.BASE_URL
import xyz.ksharma.krail.network.interceptor.AuthInterceptor.Companion.API_KEY
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GtfsServiceImpl @Inject constructor(
    private val okHttpClient: OkHttpClient,
) : GtfsService {

    override suspend fun getSydneyTrainSchedule(): Response {
        val request = Request.Builder()
            .url("$BASE_URL/v1/gtfs/schedule/sydneytrains")
            .header("Authorization", "apikey $API_KEY")
            .header("accept", "application/x-google-protobuf")
            .build()

        val response = okHttpClient.newCall(request).execute()
        // don't log it's entire response body,which is huge.
         Log.d(TAG, "fetchSydneyTrains: ${response.body?.string()?.take(500)}")
        return response
    }
}
