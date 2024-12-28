package xyz.ksharma.krail

import androidx.compose.runtime.Composable
import org.koin.compose.KoinApplication
import xyz.ksharma.krail.di.koinConfig
import xyz.ksharma.krail.taj.theme.KrailTheme

@Composable
fun KrailApp() {
    KoinApplication(application = koinConfig) {
        KrailTheme {
            KrailNavHost()
        }
    }
}
