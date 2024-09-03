package xyz.ksharma.krail.sydney.trains.network.real

import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import xyz.ksharma.krail.network.di.NetworkModule.Companion.BASE_URL
import xyz.ksharma.krail.network.interceptor.AuthInterceptor.Companion.API_KEY
import xyz.ksharma.krail.sydney.trains.network.api.SydneyTrainsService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealSydneyTrainsService @Inject constructor(
    private val okHttpClient: OkHttpClient,
) : SydneyTrainsService {

    override suspend fun getSydneyTrainsStaticData(): okhttp3.Response {
        val request = Request.Builder().url("$BASE_URL/v1/gtfs/schedule/sydneytrains")
            .header("Authorization", "apikey $API_KEY")
            .header("accept", "application/x-google-protobuf").build()

        val response = okHttpClient.newCall(request).execute()
        // don't log it's entire response body,which is huge.
        Timber.d("fetchSydneyTrains: " + response.body?.string()?.take(500))
        return response
    }
}
