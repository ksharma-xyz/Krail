package xyz.ksharma.feature1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.ksharma.krail.design.system.components.DemoRow
import xyz.ksharma.krail.design.system.theme.StartTheme
import xyz.ksharma.krail.design.system.ui.ComponentPreviews
import xyz.ksharma.krail.model.DemoData
import xyz.ksharma.krail.model.Item

@Composable
fun DemoScreen(
    modifier: Modifier = Modifier,
    demoData: DemoData,
    onItemClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier.statusBarsPadding(),
    ) {
        items(items = demoData.list) { item ->
            DemoRow(
                title = item.value,
                id = item.id,
                onItemClick = { id ->
                    onItemClick(id)
                }
            )
        }
    }
}

@ComponentPreviews
@Composable
private fun DemoScreenPreview() {
    StartTheme {
        DemoScreen(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            onItemClick = { _ -> },
            demoData = DemoData(list = listOf(Item("ABC", "12"), Item("XYZ", "11")))
        )
    }
}
