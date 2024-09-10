package xyz.ksharma.krail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.ksharma.krail.database.sydney.trains.database.api.SydneyTrainsStaticDB
import xyz.ksharma.krail.design.system.theme.StartTheme
import xyz.ksharma.krail.model.sydneytrains.GTFSFeedFileNames
import xyz.ksharma.krail.sydney.trains.database.real.parser.StopTimesParser.parseStopTimes
import xyz.ksharma.krail.sydney.trains.network.api.repository.SydneyTrainsRepository
import xyz.ksharma.krail.utils.toPath
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var realSydneyTrainsStaticDb: SydneyTrainsStaticDB

    @Inject
    lateinit var repository: SydneyTrainsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configures the system bars with a transparent background.
        enableEdgeToEdge()

        lifecycleScope.launch {
            deleteStaticGtfsFiles()

            repository.fetchStaticSydneyTrainsScheduleAndCache()

            val startTime = Instant.now()
            parseStopTimes(
                path = applicationContext.toPath(GTFSFeedFileNames.STOP_TIMES.fileName),
                ioDispatcher = Dispatchers.IO,
                db = realSydneyTrainsStaticDb
            )

            val endTime = Instant.now()
            val diff = ChronoUnit.SECONDS.between(startTime, endTime)
            Timber.d("Time taken - $diff")

            val dataSize = realSydneyTrainsStaticDb.stopTimesSize()
            Timber.d("DATA SIZE: $dataSize")
        }

        setContent {
            StartTheme {
                KrailApp()
            }
        }
    }

    private fun deleteStaticGtfsFiles() {
        val cacheDir = cacheDir
        cacheDir.listFiles()?.forEach { file ->
            if (file.isFile && file.name.endsWith(".txt")) {
                file.delete()
            }
        }
    }
}
