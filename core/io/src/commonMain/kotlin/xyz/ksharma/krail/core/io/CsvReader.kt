package xyz.ksharma.krail.core.io

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import okio.Path
import okio.buffer
import okio.use
import xyz.ksharma.krail.core.di.DispatchersComponent
import xyz.ksharma.krail.core.log.log

interface CsvReader {

    //suspend fun readCsv(path: Path): List<Stop>
}

class RealCsvReader(
    private val ioDispatcher: CoroutineDispatcher = DispatchersComponent().ioDispatcher,
) : CsvReader {

//    override suspend fun readCsv(path: Path) = withContext(ioDispatcher) {}
}

data class Stop(
    val stopId: String,
    val stopName: String,
    val stopLat: String,
    val stopLon: String,
)

suspend fun readCsvFile(path: Path): List<Stop> = withContext(Dispatchers.IO) {
    val stops = mutableListOf<Stop>()
    fileSystem.source(path).buffer().use { source ->
        val lines = source.readUtf8().lines()
        log("Lines: ${lines.size}")
        lines.drop(1).forEachIndexed { index, line ->
            log("Line: $line")

            val parts = line.split(",")
            log("Parts: $parts")

            if (parts.size >= 4 && parts[8] == "\"1\"") {
                val stop = Stop(
                    stopId = parts[0].filterNot { it == '\"' },
                    stopName = parts[2].filterNot { it == '\"' },
                    stopLat = parts[4].filterNot { it == '\"' },
                    stopLon = parts[5].filterNot { it == '\"' },
                )
                stops.add(stop)
                log("Added Stop: $stop")
            }
        }
    }
    return@withContext stops
}
