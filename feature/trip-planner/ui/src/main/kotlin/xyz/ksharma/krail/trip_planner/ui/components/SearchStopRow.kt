package xyz.ksharma.krail.trip_planner.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.LocalOnContentColor
import xyz.ksharma.krail.design.system.components.RoundIconButton
import xyz.ksharma.krail.design.system.components.TextField
import xyz.ksharma.krail.design.system.preview.ComponentPreviews
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip_planner.ui.R as TripPlannerUiR

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchStopRow(modifier: Modifier = Modifier) {

    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    var isTextFieldFocused by remember { mutableStateOf(false) }

    LaunchedEffect(isTextFieldFocused) {
        if (isTextFieldFocused) {
            bringIntoViewRequester.bringIntoView()
            keyboardController?.show()
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = KrailTheme.colors.primary,
                shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp)
            )
            .padding(vertical = 24.dp, horizontal = 16.dp)
            .bringIntoViewRequester(bringIntoViewRequester),
        horizontalArrangement = Arrangement.End,
    ) {

        Column(
            modifier = Modifier.weight(0.8f),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TextField(
                initialText = stringResource(TripPlannerUiR.string.from_text_field_placeholder),
                modifier = Modifier
                    .onFocusChanged {
                        isTextFieldFocused = it.isFocused
                    },
            )

            TextField(
                initialText = stringResource(TripPlannerUiR.string.to_text_field_placeholder),
                modifier = Modifier
                    .onFocusChanged {
                        isTextFieldFocused = it.isFocused
                    },
            )
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
