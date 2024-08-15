package xyz.ksharma.krail.data.repository

import kotlinx.coroutines.flow.Flow
import xyz.ksharma.krail.data.model.DemoDataModel

/**
 * The repository for fetching the [DemoDataModel].
 */
interface RealTimeDataRepository {

    /**
     * This method will fetch the demo data from the assets and return a cold flow.
     *
     * @return - Returns a flow of [DemoDataModel]
     */
    suspend fun fetchData(): Flow<DemoDataModel>

    suspend fun getSydneyTrains()
}
