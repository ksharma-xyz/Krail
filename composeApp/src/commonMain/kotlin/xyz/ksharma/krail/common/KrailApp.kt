package xyz.ksharma.krail.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun KrailApp() {
    Column(modifier = Modifier.fillMaxSize().background(color = Color.Blue)) {
        Text("Hello, Krail!", style = MaterialTheme.typography.h1)
    }
}
