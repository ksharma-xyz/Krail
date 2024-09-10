package xyz.ksharma.krail.sydney.trains.database.real.parser

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import xyz.ksharma.krail.database.sydney.trains.database.api.RoutesStore
import xyz.ksharma.krail.sydney.trains.database.Routes
import java.io.BufferedReader
import java.io.FileReader
import java.nio.file.Path

object RouteParser {

    suspend fun parseRoutes(
        path: Path,
        ioDispatcher: CoroutineDispatcher,
        routesStore: RoutesStore,
    ): Unit = withContext(ioDispatcher) {
        runCatching {
        val routesList = mutableListOf<Routes>()
        val transactionBatchSize = 50

            BufferedReader(FileReader(path.toString())).use { reader ->
                val headersList = reader.readLine().split(",").trimQuotes()
                // todo use headers instead of hard code later.
                //Log.d(TAG, "headersList: $headersList")

                var line: String?

                while (true) {
                    line = reader.readLine() ?: break
                    val fieldsList = line.split(",").trimQuotes()

                    routesList.add(
                        Routes(
                            route_id = fieldsList[0],
                            agency_id = fieldsList[1],
                            route_short_name = fieldsList[2],
                            route_long_name = fieldsList[3],
                            route_desc = fieldsList[4],
                            route_type = fieldsList[5].toLongOrNull(),
                            route_url = fieldsList[6],
                            route_color = fieldsList[7],
                            route_text_color = fieldsList[8],
                        )
                    )

                    if (routesList.size == transactionBatchSize) {
                        routesStore.insertRoutesBatch(routesList)
                        routesList.clear()
                    }
                }

                if (routesList.isNotEmpty()) {
                    routesStore.insertRoutesBatch(routesList)
                    routesList.clear()
                }
            }
        }.getOrElse { e ->
            Timber.e(e, "parseRoutes: ")
        }
    }
}
