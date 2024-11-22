package xyz.ksharma.krail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication
import org.koin.dsl.includes
import org.koin.dsl.koinConfiguration
import xyz.ksharma.krail.common.KrailApp
import xyz.ksharma.krail.common.di.koinConfig
import xyz.ksharma.krail.sandook.androidDbModule

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KrailApp()
        }
    }

    /*
        private val koinConfig1 = koinConfiguration {
            androidContext(androidContext = this@MainActivity.applicationContext)
            modules(androidDbModule)
            includes(koinConfig)
        }
    */
}
