package xyz.ksharma.krail.sydney.trains.database.real.di

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import xyz.ksharma.krail.database.sydney.trains.database.api.SydneyTrainsStaticDB
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher
import xyz.ksharma.krail.sydney.trains.database.StopTimes
import xyz.ksharma.krail.sydney_trains.database.api.KrailDB
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealSydneyTrainsStaticDb @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    coroutineScope: CoroutineScope,
    @ApplicationContext private val applicationContext: Context,
) : SydneyTrainsStaticDB {

    private val sydneyTrainsDB: Deferred<KrailDB> by lazy {
        coroutineScope.async(ioDispatcher) {
            KrailDB(
                AndroidSqliteDriver(
                    KrailDB.Schema,
                    applicationContext,
                    "sydney_trains_static.db"
                )
            )
        }
    }

    private suspend fun getSydneyTrainsDb(): KrailDB = sydneyTrainsDB.await()

    override suspend fun insertStopTimes() {
        getSydneyTrainsDb().stoptimesQueries.insertIntoStopTime(
            trip_id = "id_1",
            arrival_time = "arr",
            departure_time = "depart",
            stop_id = "",
            stop_sequence = 1,
            stop_headsign = "",
            pickup_type = 1,
            drop_off_type = 1,
        )
    }

    override suspend fun getStopTimes(): List<StopTimes> {
        val all = getSydneyTrainsDb().stoptimesQueries.selectAll()
        return all.executeAsList()
    }

    override suspend fun clearStopTimes() {
        TODO("Not yet implemented")
    }
}
