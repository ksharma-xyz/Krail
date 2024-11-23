package xyz.ksharma.krail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import xyz.ksharma.krail.KrailApp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

/*
        val applicationComponent = AndroidApplicationComponent.from(this)
*/

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

/*
private fun AndroidApplicationComponent.Companion.from(context: Context): AndroidApplicationComponent {
    return (context.applicationContext as KrailApplication).component
}
*/
