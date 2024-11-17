package xyz.ksharma.krail.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.russhwolf.settings.Settings
import kotlinx.coroutines.delay
import xyz.ksharma.krail.sandook.RealSandook
import xyz.ksharma.krail.taj.components.Text
import xyz.ksharma.krail.taj.theme.KrailTheme

@Composable
fun KrailApp() {
    KrailTheme {

        LaunchedEffect(Unit) {
            val sandook = RealSandook(Settings())
            println("sandook: " + sandook)
            sandook.putString("key", "value")

            delay(2000)
            val sandook1 = RealSandook(Settings())
            println("sandook1: " + sandook1)
            var x = sandook1.getString("key")
            println("Sandook1 value: x: $x")
            x = sandook.getString("key")
            println("Sandook value: x: $x")
        }



        Column(modifier = Modifier.fillMaxSize().background(color = KrailTheme.colors.surface)) {
            Text(
                "Hello, Krail!",
                style = KrailTheme.typography.titleLarge,
                modifier = Modifier.statusBarsPadding()
            )
        }
    }
}
