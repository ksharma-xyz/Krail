package xyz.ksharma.krail

import androidx.compose.runtime.Composable
import org.koin.compose.KoinContext
import xyz.ksharma.krail.taj.theme.KrailTheme

@Composable
fun KrailApp() {
    KoinContext {
        KrailTheme {
            KrailNavHost()
        }
    }
}
