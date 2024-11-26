package xyz.ksharma.krail.trip.planner.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import krail.feature.trip_planner.ui.generated.resources.Res
import krail.feature.trip_planner.ui.generated.resources.ic_reverse
import krail.feature.trip_planner.ui.generated.resources.ic_search
import org.jetbrains.compose.resources.painterResource
import xyz.ksharma.krail.taj.LocalOnContentColor
import xyz.ksharma.krail.taj.LocalThemeColor
import xyz.ksharma.krail.taj.components.RoundIconButton
import xyz.ksharma.krail.taj.components.Text
import xyz.ksharma.krail.taj.components.TextFieldButton
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.searchstop.model.StopItem

@Composable
fun SearchStopRow(
    fromButtonClick: () -> Unit,
    toButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    fromStopItem: StopItem? = null,
    toStopItem: StopItem? = null,
    onReverseButtonClick: () -> Unit = {},
    onSearchButtonClick: () -> Unit = {},
) {
    val themeColor by LocalThemeColor.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = themeColor.hexToComposeColor(),
                shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp),
            )
            .padding(vertical = 24.dp, horizontal = 16.dp)
            .padding(
                bottom = with(LocalDensity.current) {
                    WindowInsets.navigationBars
                        .getBottom(this)
                        .toDp()
                },
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            TextFieldButton(onClick = fromButtonClick) {
                Text(
                    text = fromStopItem?.stopName
                        ?: "Starting from",
                    maxLines = 1,
                )
            }
            TextFieldButton(onClick = toButtonClick) {
                Text(
                    text = toStopItem?.stopName
                        ?: "Destination",
                    maxLines = 1,
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp), // TODO - token "SearchFieldSpacing"
        ) {
            RoundIconButton(
                content = {
                    Image(
                        painter = painterResource(Res.drawable.ic_reverse),
                        contentDescription = "Reverse",
                        colorFilter = ColorFilter.tint(LocalOnContentColor.current),
                        modifier = Modifier.size(24.dp),
                    )
                },
                onClick = onReverseButtonClick,
            )

            RoundIconButton(
                content = {
                    Image(
                        painter = painterResource(Res.drawable.ic_search),
                        contentDescription = "Search",
                        colorFilter = ColorFilter.tint(LocalOnContentColor.current),
                        modifier = Modifier.size(24.dp),
                    )
                },
                onClick = onSearchButtonClick,
            )
        }
    }
}

// region Previews

@Composable
private fun SearchStopColumnPreview() {
    KrailTheme {
        val themeColor = remember { mutableStateOf(TransportMode.Train().colorCode) }
        CompositionLocalProvider(LocalThemeColor provides themeColor) {
            SearchStopRow(
                fromButtonClick = {},
                toButtonClick = {},
            )
        }
    }
}

// endregion
