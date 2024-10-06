package xyz.ksharma.krail.trip_planner.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.LocalOnContentColor
import xyz.ksharma.krail.design.system.components.RoundIconButton
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.components.TextFieldButton
import xyz.ksharma.krail.design.system.preview.ComponentPreviews
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip_planner.ui.R as TripPlannerUiR

@Composable
fun SearchStopRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = KrailTheme.colors.primary,
                shape = RoundedCornerShape(36.dp)
            )
            .padding(vertical = 24.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.End,
    ) {

        Column(
            modifier = Modifier.weight(0.8f),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TextFieldButton {
                Text(text = stringResource(TripPlannerUiR.string.from_text_field_placeholder))
            }
            TextFieldButton{
                Text(text = stringResource(TripPlannerUiR.string.to_text_field_placeholder))
            }
        }

        Column(
            modifier = Modifier
                .weight(0.2f)
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp) // TODO - token "SearchFieldSpacing"
        ) {
            RoundIconButton(
                content = {
                    Image(
                        imageVector = ImageVector.vectorResource(TripPlannerUiR.drawable.ic_search),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(LocalOnContentColor.current),
                    )
                },
                onClick = {},
            )

            RoundIconButton(
                content = {
                    Image(
                        imageVector = ImageVector.vectorResource(TripPlannerUiR.drawable.ic_search),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(LocalOnContentColor.current),
                    )
                },
                onClick = {},
            )
        }
    }
}

// region Previews

@ComponentPreviews
@Composable
private fun SearchStopColumnPreview() {
    KrailTheme {
        SearchStopRow()
    }
}

// endregion