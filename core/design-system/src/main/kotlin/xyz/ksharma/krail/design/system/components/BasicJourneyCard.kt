package xyz.ksharma.krail.design.system.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.preview.ComponentPreviews
import xyz.ksharma.krail.design.system.theme.KrailTheme

@Composable
internal fun BasicJourneyCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = KrailTheme.colors.secondaryContainer,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(backgroundColor)
        .semantics(mergeDescendants = true) {}
        .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        content()
    }
}

@ComponentPreviews
@Composable
private fun BasicJourneyCardPreview() {
    KrailTheme {
        BasicJourneyCard(
            content = {
                Text("content")
            },
        )
    }
}
