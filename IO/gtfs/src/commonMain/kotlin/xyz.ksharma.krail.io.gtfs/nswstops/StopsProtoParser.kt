package xyz.ksharma.krail.io.gtfs.nswstops

import app.krail.kgtfs.proto.NswStopList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.until
import krail.io.gtfs.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import xyz.ksharma.krail.core.log.log
import xyz.ksharma.krail.sandook.Sandook

class StopsProtoParser(
    private val ioDispatcher: CoroutineDispatcher,
    private val sandook: Sandook,
) : ProtoParser {

    @OptIn(ExperimentalResourceApi::class)
    override suspend fun readStops(): NswStopList =
        withContext(ioDispatcher) {
            var start = Clock.System.now()

            val byteArray = Res.readBytes("files/NSW_STOPS.pb")
            val decoded = NswStopList.ADAPTER.decode(byteArray)

            var duration = start.until(
                Clock.System.now(), DateTimeUnit.MILLISECOND, TimeZone.currentSystemDefault()
            )
            log("Decoded #Stops: ${decoded.nswStops.size} - duration: $duration ms")

            log("Start inserting stops. Currently ${sandook.stopsCount()} stops in the database")
            start = Clock.System.now()
            insertStopsInTransaction(decoded)

            duration = start.until(
                Clock.System.now(), DateTimeUnit.MILLISECOND, TimeZone.currentSystemDefault()
            )
            log("Inserted #Stops: ${decoded.nswStops.size} in duration: $duration ms")

            decoded
        }

    private suspend fun insertStopsInTransaction(decoded: NswStopList) = withContext(ioDispatcher) {
        sandook.nswStopsQueries.transaction {
            decoded.nswStops.forEach { nswStop ->
                sandook.insertNswStop(
                    stopId = nswStop.stopId,
                    stopName = nswStop.stopName,
                    stopLat = nswStop.lat,
                    stopLon = nswStop.lon
                )
                nswStop.productClass.forEach { productClass ->
                    sandook.insertNswStopProductClass(
                        stopId = nswStop.stopId,
                        productClass = productClass
                    )
                }
            }
        }
        // Log less frequently, for example once after the transaction completes
        println("Inserted ${decoded.nswStops.size} stops in a single transaction.")
    }
}
