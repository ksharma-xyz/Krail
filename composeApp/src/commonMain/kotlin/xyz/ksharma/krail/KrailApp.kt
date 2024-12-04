package xyz.ksharma.krail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import org.koin.compose.KoinApplication
import xyz.ksharma.krail.core.appinfo.LocalPlatformTypeProvider
import xyz.ksharma.krail.core.appinfo.getPlatform
import xyz.ksharma.krail.di.koinConfig
import xyz.ksharma.krail.taj.theme.KrailTheme

@Composable
fun KrailApp() {
    KoinApplication(application = koinConfig) {
        CompositionLocalProvider(LocalPlatformTypeProvider provides getPlatform().type) {
            KrailTheme {
                KrailNavHost()
            }
        }
    }
}
