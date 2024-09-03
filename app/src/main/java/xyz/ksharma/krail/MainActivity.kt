package xyz.ksharma.krail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.ksharma.krail.database.api.SydneyTrainsStaticDB
import xyz.ksharma.krail.design.system.theme.StartTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var realSydneyTrainsStaticDb: SydneyTrainsStaticDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configures the system bars with a transparent background.
        enableEdgeToEdge()

        lifecycleScope.launch {
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
