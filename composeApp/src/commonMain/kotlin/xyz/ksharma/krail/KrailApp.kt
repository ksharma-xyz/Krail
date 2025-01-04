package xyz.ksharma.krail

import androidx.compose.runtime.Composable
import org.koin.compose.KoinApplication
import org.koin.dsl.KoinAppDeclaration
import xyz.ksharma.krail.di.koinConfig
import xyz.ksharma.krail.taj.theme.KrailTheme

@Composable
fun KrailApp(koinDeclaration: KoinAppDeclaration? = null) {
    KoinApplication(application = {
        koinDeclaration?.invoke(this)
        koinConfig()
    }) {
        KrailTheme {
            KrailNavHost()
        }
    }
}
