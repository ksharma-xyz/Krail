package xyz.ksharma.krail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.ksharma.krail.database.sydney.trains.database.api.SydneyTrainsStaticDB
import xyz.ksharma.krail.design.system.theme.StartTheme
import xyz.ksharma.krail.sydney.trains.network.api.repository.SydneyTrainsRepository
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
            repository.fetchStaticSydneyTrainsScheduleAndCache()
            realSydneyTrainsStaticDb.insertStopTimes()
            val x = realSydneyTrainsStaticDb.getStopTimes()
            Timber.d("$x")
        }

        setContent {
            StartTheme {
                KrailApp()
            }
        }
    }
}
