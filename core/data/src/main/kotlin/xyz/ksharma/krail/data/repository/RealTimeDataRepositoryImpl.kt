package xyz.ksharma.krail.data.repository

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher
import xyz.ksharma.krail.network.RealTimeService
import javax.inject.Inject

class RealTimeDataRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val realTimeService: RealTimeService,
) : RealTimeDataRepository {

    private val TAG = "RealTimeDataRepositoryI"

    override suspend fun getSydneyTrains() {
        Log.d(TAG, "getSydneyTrains: ")
        val x = realTimeService.getRealtimeSydneyTrainsSchedule()
    }
}
