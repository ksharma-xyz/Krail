package xyz.ksharma.krail.common

import androidx.compose.runtime.Composable
import xyz.ksharma.krail.taj.theme.KrailTheme

@Composable
fun KrailApp() {
    KrailTheme {
        KrailNavHost()
    }
}
