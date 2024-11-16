package xyz.ksharma.krail.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.ksharma.krail.taj.components.Text
import xyz.ksharma.krail.taj.theme.KrailTheme

@Composable
fun KrailApp() {
    KrailTheme {
        Column(modifier = Modifier.fillMaxSize().background(color = KrailTheme.colors.surface)) {
            Text(
                "Hello, Krail!",
                style = KrailTheme.typography.titleLarge,
                modifier = Modifier.statusBarsPadding()
            )
        }
    }
}
