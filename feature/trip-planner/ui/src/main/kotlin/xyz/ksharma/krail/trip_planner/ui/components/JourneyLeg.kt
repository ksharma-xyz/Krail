package xyz.ksharma.krail.trip_planner.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import xyz.ksharma.krail.design.system.theme.KrailTheme

@Composable
fun JourneyLeg(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewJourneyLeg() {
    KrailTheme {
        JourneyLeg()
    }
}
