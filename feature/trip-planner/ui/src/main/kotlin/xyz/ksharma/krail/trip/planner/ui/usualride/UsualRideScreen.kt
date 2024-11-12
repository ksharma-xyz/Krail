package xyz.ksharma.krail.trip.planner.ui.usualride

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip.planner.ui.components.TransportModeIcon
import xyz.ksharma.krail.trip.planner.ui.components.hexToComposeColor
import xyz.ksharma.krail.trip.planner.ui.components.transportModeBackgroundColor
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.TransportModeSortOrder

private val TransportMode.tagLine: String
    get() {
        // TODO - read from remote config
        return when (this) {
            is TransportMode.Bus -> "Hoppin' the concrete jungle!"
            is TransportMode.Coach -> "Coachin' it!"
            is TransportMode.Ferry -> "Just floatin!"
            is TransportMode.LightRail -> "Mah city, mah rules!"
            is TransportMode.Metro -> "Surf the sub, no cap!"
            is TransportMode.Train -> "On the track, no lookin' back!"
        }
    }

@Composable
fun UsualRideScreen(
    transportModes: ImmutableSet<TransportMode>,
    transportModeSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = KrailTheme.colors.surface)
            .statusBarsPadding(),
    ) {
        var selectedProductClass: Int? by rememberSaveable { mutableStateOf(null) }

        LazyColumn(contentPadding = PaddingValues(top = 24.dp, bottom = 152.dp)) {
            item {
                Text(
                    text = "Let's set the vibe.",
                    style = KrailTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Normal),
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 8.dp, top = 16.dp),
                )
            }

            item {
                Text(
                    text = "What's your favourite color, mate?",
                    style = KrailTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 16.dp),
                )
            }

            items(items = transportModes.toImmutableList(), key = { it.productClass }) { mode ->
                TransportModeRadioButton(
                    mode = mode,
                    selected = selectedProductClass == mode.productClass,
                    onClick = { clickedMode ->
                        selectedProductClass = clickedMode.productClass
                    },
                )
            }
        }

        Text(
            text = if (selectedProductClass != null) "Let's Go, Yeah!" else "Pick one.",
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(color = KrailTheme.colors.surface)
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .clip(RoundedCornerShape(50))
                .padding(vertical = 24.dp, horizontal = 24.dp)
                .border(
                    width = 5.dp,
                    color = selectedProductClass?.let {
                        TransportMode.toTransportModeType(it)
                    }?.colorCode?.hexToComposeColor() ?: KrailTheme.colors.surface,
                    shape = RoundedCornerShape(50),
                )
                .clickable(
                    role = Role.Button,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {
                    selectedProductClass?.let { productClass ->
                        transportModeSelected(productClass)
                    }
                }
                .padding(vertical = 12.dp),
            textAlign = TextAlign.Center,
            style = KrailTheme.typography.titleMedium,
        )
    }
}

@Composable
private fun TransportModeRadioButton(
    mode: TransportMode,
    onClick: (TransportMode) -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp)
            .background(color = if (selected) transportModeBackgroundColor(mode) else Color.Transparent)
            .padding(vertical = 16.dp, horizontal = 24.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                role = Role.Button,
            ) { onClick(mode) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TransportModeIcon(
            letter = mode.name.first(),
            backgroundColor = mode.colorCode.hexToComposeColor(),
            iconSize = 32.sp,
            fontSize = 18.sp,
        )

        Column(
            modifier = Modifier.padding(start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = mode.name,
                style = KrailTheme.typography.titleMedium,
            )

            Text(
                text = mode.tagLine,
                style = KrailTheme.typography.body.copy(fontWeight = FontWeight.Normal),
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewUsualRideScreen() {
    KrailTheme {
        UsualRideScreen(
            TransportMode.sortedValues(sortOrder = TransportModeSortOrder.PRODUCT_CLASS)
                .toImmutableSet(),
            transportModeSelected = {},
        )
    }
}
