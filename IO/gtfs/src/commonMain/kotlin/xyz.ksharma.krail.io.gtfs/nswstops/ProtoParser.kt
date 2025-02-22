package xyz.ksharma.krail.io.gtfs.nswstops

import app.krail.kgtfs.proto.NswStopList
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.until
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import okio.buffer
import okio.use
import xyz.ksharma.krail.core.log.log

interface ProtoParser {
    fun readProtoFile(fileName: String): NswStopList?
}

class StopsProtoParser(private val resourceFactory: ResourceFactory) : ProtoParser {

    override fun readProtoFile(fileName: String): NswStopList? {
        val start = Clock.System.now()

        val path1 = getResourcePath("NSW_STOPS.pb")
        log("path: $path1")

        val fileSystem = FileSystem.SYSTEM
        val path = getResourcePath(fileName)?.toPath()
            ?: error("File not found: $fileName")

        val decoded = fileSystem.source(path).use { source ->
            source.buffer().readByteArray().let { byteArray ->
                NswStopList.ADAPTER.decode(byteArray)
            }
        }

        val duration = start.until(
            Clock.System.now(), DateTimeUnit.MILLISECOND, TimeZone.currentSystemDefault()
        )

        println("Decoded: ${'$'}{decoded.nswStops.size} - duration: $duration ms")
        return null
//        return decoded
    }

    private fun getResourcePath(fileName: String): String? {
        return resourceFactory.getResourcePath(fileName)
    }
}
