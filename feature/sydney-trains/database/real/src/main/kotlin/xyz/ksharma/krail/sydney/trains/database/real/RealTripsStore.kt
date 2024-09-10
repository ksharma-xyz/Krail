package xyz.ksharma.krail.sydney.trains.database.real

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import xyz.ksharma.krail.database.sydney.trains.database.api.TripsStore
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher
import xyz.ksharma.krail.sydney.trains.database.Trip
import xyz.ksharma.krail.sydney_trains.database.api.KrailDB
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealTripsStore @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    /*
        coroutineScope: CoroutineScope,
        @ApplicationContext private val applicationContext: Context,
    */
    private val krailDB: KrailDB,
) : TripsStore {

    override suspend fun insertTrip(
        routeId: String,
        serviceId: String,
        tripId: String,
        tripHeadsign: String?,
        tripShortName: String?,
        directionId: Long?,
        blockId: String?,
        shapeId: String?,
        wheelchairAccessible: Long?,
    ) = withContext(ioDispatcher) {
        krailDB.tripsQueries.insertIntoTrips(
            route_id = routeId,
            service_id = serviceId,
            trip_id = tripId,
            trip_headsign = tripHeadsign,
            trip_short_name = tripShortName,
            direction_id = directionId,
            block_id = blockId,
            shape_id = shapeId,
            wheelchair_accessible = wheelchairAccessible,
        )
    }

    override suspend fun clearTrips() = withContext(ioDispatcher) {
        TODO()
    }

    override suspend fun insertTripsBatch(tripsList: List<Trip>) = withContext(ioDispatcher) {
        krailDB.transaction {
            tripsList.forEach { trip ->
                with(trip) {
                    krailDB.tripsQueries.insertIntoTrips(
                        route_id = route_id,
                        service_id = service_id,
                        trip_id = trip_id,
                        trip_headsign = trip_headsign,
                        trip_short_name = trip_short_name,
                        direction_id = direction_id,
                        block_id = block_id,
                        shape_id = shape_id,
                        wheelchair_accessible = wheelchair_accessible,
                    )
                }
            }
        }
    }

    override suspend fun getAllTrips(): List<Trip> = withContext(ioDispatcher) {
        krailDB.tripsQueries.selectAllTrips().executeAsList()
    }

    override suspend fun tripsCount(): Long = withContext(ioDispatcher) {
        krailDB.tripsQueries.tripsCount().executeAsOne()
    }
}
