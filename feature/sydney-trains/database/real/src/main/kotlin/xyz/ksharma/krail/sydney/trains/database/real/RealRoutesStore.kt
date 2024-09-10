package xyz.ksharma.krail.sydney.trains.database.real

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import xyz.ksharma.krail.database.sydney.trains.database.api.RoutesStore
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher
import xyz.ksharma.krail.sydney.trains.database.Routes
import xyz.ksharma.krail.sydney_trains.database.api.KrailDB
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealRoutesStore @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val krailDB: KrailDB,
) : RoutesStore {

    override suspend fun insertRoute(
        routeId: String,
        agencyId: String?,
        routeShortName: String,
        routeLongName: String,
        routeDesc: String?,
        routeType: Long,
        routeUrl: String?,
        routeColor: String?,
        routeTextColor: String?,
    ) = withContext(ioDispatcher) {
        krailDB.routesQueries.insertIntoRoutes(

            route_id = routeId,
            agency_id = agencyId,
            route_short_name = routeShortName,
            route_long_name = routeLongName,
            route_desc = routeDesc,
            route_type = routeType,
            route_url = routeUrl,
            route_color = routeColor,
            route_text_color = routeTextColor,
        )
    }

    override suspend fun insertRoutesBatch(routesList: List<Routes>) {
        krailDB.transaction {
            routesList.forEach { route ->
                with(route) {
                    krailDB.routesQueries.insertIntoRoutes(
                        route_id = route_id,
                        agency_id = agency_id,
                        route_short_name = route_short_name,
                        route_long_name = route_long_name,
                        route_desc = route_desc,
                        route_type = route_type,
                        route_url = route_url,
                        route_color = route_color,
                        route_text_color = route_text_color,
                    )
                }
            }
        }
    }

    override suspend fun getAllRoutes(): List<Routes> = withContext(ioDispatcher) {
        krailDB.routesQueries.selectAllRoutes().executeAsList()
    }

    override suspend fun routesCount(): Long = withContext(ioDispatcher) {
        krailDB.routesQueries.routesCount().executeAsOne()
    }

    override suspend fun clearRoutes() = withContext(ioDispatcher) { TODO() }
}
