package xyz.ksharma.krail.sydney.trains.network.real.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.Response
import timber.log.Timber
import xyz.ksharma.krail.database.sydney.trains.database.api.ZipEntryCacheManager
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher
import xyz.ksharma.krail.sydney.trains.network.api.SydneyTrainsService
import xyz.ksharma.krail.sydney.trains.network.api.repository.SydneyTrainsRepository
import javax.inject.Inject

class RealSydneyTrainsRepository @Inject constructor(
    @ApplicationContext val context: Context,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val gtfsService: SydneyTrainsService,
    private val cacheManager: ZipEntryCacheManager,
) : SydneyTrainsRepository {

    override suspend fun fetchStaticSydneyTrainsScheduleAndCache(): Unit =
        withContext(ioDispatcher) {
            Timber.d("getSydneyTrains: ")
            val result: Result<Response> = gtfsService.getSydneyTrainsStaticData()

            Timber.d("Response received")
            if (result.isSuccess) {
                result.getOrNull()?.let { response ->
                    // TODO - provide boolean response if files were cached successfully.
                    cacheManager.cacheZipResponse(response)
                }
            } else if (result.isFailure) {
                Timber.e("Error fetching Sydney Trains static GTFS data: ${result.exceptionOrNull()}")
            }
        }
}
