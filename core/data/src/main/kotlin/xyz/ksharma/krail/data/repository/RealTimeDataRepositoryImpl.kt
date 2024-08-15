package xyz.ksharma.krail.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import xyz.ksharma.krail.data.model.DemoDataModel
import xyz.ksharma.krail.data.util.parseJsonToDataModel
import xyz.ksharma.krail.data.util.readJsonFromAssets
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher
import xyz.ksharma.krail.network.RealTimeService
import javax.inject.Inject

class RealTimeDataRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val realTimeService: RealTimeService,
) : RealTimeDataRepository {

    override suspend fun fetchData(): Flow<DemoDataModel> =
        withContext(ioDispatcher) {
            val jsonString = context.readJsonFromAssets(fileName = "demo.json")
            return@withContext flow { emit(jsonString.parseJsonToDataModel()) }
        }

    override suspend fun getSydneyTrains() {
        realTimeService.getRealtimeSydneyTrainsSchedule()
    }
}
