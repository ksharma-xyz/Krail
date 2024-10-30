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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.LocalOnContentColor
import xyz.ksharma.krail.design.system.components.RoundIconButton
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.components.TextFieldButton
import xyz.ksharma.krail.design.system.preview.PreviewComponent
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.searchstop.model.StopItem
import xyz.ksharma.krail.trip.planner.ui.R as TripPlannerUiR

@Composable
fun SearchStopRow(
    themeColor: Color,
    fromButtonClick: () -> Unit,
    toButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    fromStopItem: StopItem? = null,
    toStopItem: StopItem? = null,
    onReverseButtonClick: () -> Unit = {},
    onSearchButtonClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = themeColor,
                shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp),
            )
            .padding(vertical = 24.dp, horizontal = 16.dp)
            .padding(
                bottom = with(LocalDensity.current) {
                    WindowInsets.navigationBars
                        .getBottom(
                            this,
                        )
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
                        ?: stringResource(TripPlannerUiR.string.from_text_field_placeholder),
                    maxLines = 1,
                )
            }
            TextFieldButton(onClick = toButtonClick) {
                Text(
                    text = toStopItem?.stopName
                        ?: stringResource(TripPlannerUiR.string.to_text_field_placeholder),
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
                        imageVector = ImageVector.vectorResource(TripPlannerUiR.drawable.ic_reverse),
                        contentDescription = "Reverse",
                        colorFilter = ColorFilter.tint(LocalOnContentColor.current),
                    )
                },
                onClick = onReverseButtonClick,
            )

            RoundIconButton(
                content = {
                    Image(
                        imageVector = ImageVector.vectorResource(TripPlannerUiR.drawable.ic_search),
                        contentDescription = "Search",
                        colorFilter = ColorFilter.tint(LocalOnContentColor.current),
                    )
                },
                onClick = onSearchButtonClick,
            )
        }
    }
}

// region Previews

@PreviewComponent
@Composable
private fun SearchStopColumnPreview() {
    KrailTheme {
        SearchStopRow(
            fromButtonClick = {},
            toButtonClick = {},
            themeColor = TransportMode.Train().colorCode.hexToComposeColor(),
        )
    }
}

// endregion
