package xyz.ksharma.krail.sydney.trains.database.real

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import xyz.ksharma.krail.database.sydney.trains.database.api.StopsStore
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher
import xyz.ksharma.krail.sydney.trains.database.Stop
import xyz.ksharma.krail.sydney_trains.database.api.KrailDB
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealStopsStore @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val krailDB: KrailDB,
) : StopsStore {

    override suspend fun insertStop(
        stopId: String,
        stopCode: String?,
        stopName: String,
        stopDesc: String?,
        stopLat: Double,
        stopLon: Double,
        zoneId: String?,
        stopUrl: String?,
        locationType: Long?,
        parentStation: String?,
        stopTimezone: String?,
        wheelchairBoarding: Long?,
    ) = withContext(ioDispatcher) {
        krailDB.stopsQueries.insertIntoStops(
            stop_id = stopId,
            stop_code = stopCode,
            stop_name = stopName,
            stop_desc = stopDesc,
            stop_lat = stopLat,
            stop_lon = stopLon,
            zone_id = zoneId,
            stop_url = stopUrl,
            location_type = locationType,
            parent_station = parentStation,
            stop_timezone = stopTimezone,
            wheelchair_boarding = wheelchairBoarding,
        )
    }

    override suspend fun insertStopsBatch(stopsList: List<Stop>) = withContext(ioDispatcher) {
        krailDB.stopsQueries.transaction {
            stopsList.forEach { stop ->
                with(stop) {
                    krailDB.stopsQueries.insertIntoStops(
                        stop_id = stop_id,
                        stop_code = stop_code,
                        stop_name = stop_name,
                        stop_desc = stop_desc,
                        stop_lat = stop_lat,
                        stop_lon = stop_lon,
                        zone_id = zone_id,
                        stop_url = stop_url,
                        location_type = location_type,
                        parent_station = parent_station,
                        stop_timezone = stop_timezone,
                        wheelchair_boarding = wheelchair_boarding,
                    )
                }
            }
        }
    }

    override suspend fun getAllStops(): List<Stop> = withContext(ioDispatcher) {
        krailDB.stopsQueries.selectAllStops().executeAsList()
    }

    override suspend fun stopsCount(): Long = withContext(ioDispatcher) {
        krailDB.stopsQueries.stopsCount().executeAsOne()
    }

    override suspend fun clearStops() = withContext(ioDispatcher) {
        TODO("Not yet implemented")
    }
}
