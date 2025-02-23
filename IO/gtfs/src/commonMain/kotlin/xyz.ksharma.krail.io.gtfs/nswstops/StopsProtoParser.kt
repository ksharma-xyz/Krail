package xyz.ksharma.krail.io.gtfs.nswstops

import app.krail.kgtfs.proto.NswStopList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.until
import krail.io.gtfs.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import xyz.ksharma.krail.core.log.log

class StopsProtoParser(private val ioDispatcher: CoroutineDispatcher) : ProtoParser {

    @OptIn(ExperimentalResourceApi::class)
    override suspend fun readStops(): NswStopList =
        withContext(ioDispatcher) {
            val start = Clock.System.now()

            val byteArray = Res.readBytes("files/NSW_STOPS.pb")
            val decoded = NswStopList.ADAPTER.decode(byteArray)

            val duration = start.until(
                Clock.System.now(), DateTimeUnit.MILLISECOND, TimeZone.currentSystemDefault()
            )
            log("Decoded #Stops: ${decoded.nswStops.size} - duration: $duration ms")
            decoded
        }
}
