package xyz.ksharma.krail.sydney.trains.database.real

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import timber.log.Timber
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

    override suspend fun insertStopTimes(
        tripId: String,
        arrivalTime: String,
        departureTime: String,
        stopId: String,
        stopSequence: Int?,
        stopHeadsign: String,
        pickupType: Int?,
        dropOffType: Int?,
    ) {
        getSydneyTrainsDb().stoptimesQueries.insertIntoStopTime(
            trip_id = tripId,
            arrival_time = arrivalTime,
            departure_time = departureTime,
            stop_id = stopId,
            stop_sequence = stopSequence?.toLong(),
            stop_headsign = stopHeadsign,
            pickup_type = pickupType?.toLong(),
            drop_off_type = dropOffType?.toLong(),
        )
    }

    override suspend fun getStopTimes(): List<StopTimes> {
        val all = getSydneyTrainsDb().stoptimesQueries.selectAll()
        return all.executeAsList()
    }

    override suspend fun insertStopTimesBatch(stopTimesList: List<StopTimes>) =
        with(sydneyTrainsDB.await().stoptimesQueries) {
//            Timber.d("insertStopTimesBatch: size:${stopTimesList.size} -> first(trip_id):${stopTimesList.first().trip_id}")

            getSydneyTrainsDb().transaction {
                stopTimesList.forEach { stopTime ->
                    insertIntoStopTime(
                        trip_id = stopTime.trip_id,
                        arrival_time = stopTime.arrival_time,
                        departure_time = stopTime.departure_time,
                        stop_id = stopTime.stop_id,
                        stop_sequence = stopTime.stop_sequence,
                        stop_headsign = stopTime.stop_headsign,
                        pickup_type = stopTime.pickup_type,
                        drop_off_type = stopTime.drop_off_type,
                    )
                }
            }
        }

    override suspend fun stopTimesSize(): Long = getSydneyTrainsDb().stoptimesQueries.sizeOfStopTimes().executeAsOne()

    override suspend fun clearStopTimes() {
        TODO("Not yet implemented")
    }
}
