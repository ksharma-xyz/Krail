package xyz.ksharma.krail.sydney.trains.network.real

import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import xyz.ksharma.krail.coroutines.ext.safeResult
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher
import xyz.ksharma.krail.network.BuildConfig.NSW_TRANSPORT_API_KEY
import xyz.ksharma.krail.network.di.NetworkModule.BASE_URL
import xyz.ksharma.krail.sydney.trains.network.api.SydneyTrainsService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealSydneyTrainsService @Inject constructor(
    private val okHttpClient: OkHttpClient,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : SydneyTrainsService {

    override suspend fun getSydneyTrainsStaticData(): Result<Response> {
        val request = Request.Builder()
            .url("$BASE_URL/v1/gtfs/schedule/sydneytrains")
            .header("Authorization", "apikey $NSW_TRANSPORT_API_KEY")
            .build()

        return safeResult(ioDispatcher) {
            okHttpClient.newCall(request).execute()
        }
    }
}
