package xyz.ksharma.krail.core.appstart

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import xyz.ksharma.krail.core.log.log
import xyz.ksharma.krail.core.remote_config.RemoteConfig
import xyz.ksharma.krail.io.gtfs.nswstops.ProtoParser

class RealAppStart(
    private val coroutineScope: CoroutineScope,
    private val remoteConfig: RemoteConfig,
    private val protoParser: ProtoParser,
) : AppStart {
    override fun start() {
        coroutineScope.launch {

            remoteConfig.setup() // App Start Event

            kotlin.runCatching {
                log("Reading NSW_STOPS proto file - StopsParser:")
                // Call only if stops are not inserted in the database.
                protoParser.parseAndInsertStops()
            }.getOrElse {
                log("Error reading proto file: $it")
                // TODO - Firebase performance track.
            }
        }
    }
}
