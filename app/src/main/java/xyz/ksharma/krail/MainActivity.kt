package xyz.ksharma.krail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import xyz.ksharma.krail.design.system.theme.StartTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configures the system bars with a transparent background.
        enableEdgeToEdge()

        setContent {
            StartTheme {
                KrailApp()
            }
        }
    }
}
