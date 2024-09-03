package xyz.ksharma.krail.sydney.trains.network.real.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.Response
import timber.log.Timber
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher
import xyz.ksharma.krail.sydney.trains.network.api.SydneyTrainsService
import xyz.ksharma.krail.sydney.trains.network.api.repository.SydneyTrainsRepository
import xyz.ksharma.krail.network.cacheZipResponse
import javax.inject.Inject

class RealSydneyTrainsRepository @Inject constructor(
    @ApplicationContext val context: Context,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val gtfsService: SydneyTrainsService,
) : SydneyTrainsRepository {

    override suspend fun fetchStaticSydneyTrainsScheduleAndCache(): Unit = withContext(ioDispatcher) {
        Timber.d( "getSydneyTrains: ")
        val response: Response = gtfsService.getSydneyTrainsStaticData()
        response.cacheZipResponse(dispatcher = ioDispatcher, context = context)

        // TODO - provide boolean response if files were cached successfully.
    }
}
