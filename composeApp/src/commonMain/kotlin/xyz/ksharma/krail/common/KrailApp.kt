package xyz.ksharma.krail.common

import androidx.compose.runtime.Composable
import org.koin.compose.KoinApplication
import xyz.ksharma.krail.common.di.koinConfig

import xyz.ksharma.krail.taj.theme.KrailTheme

@Composable
fun KrailApp() {
    KoinApplication(application = koinConfig) {
        KrailTheme {
            KrailNavHost()
        }
    }
}
