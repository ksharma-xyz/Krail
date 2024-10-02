package xyz.ksharma.krail.design.system.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.ksharma.krail.design.system.preview.ComponentPreviews
import xyz.ksharma.krail.design.system.theme.KrailTheme

@Composable
fun JourneyDetailCard(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {

    }
}

// region Previews

@ComponentPreviews
@Composable
private fun JourneyDetailCardPreview(modifier: Modifier = Modifier) {
    KrailTheme {
        JourneyDetailCard()
    }
}

// endregion
