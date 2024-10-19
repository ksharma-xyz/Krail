package xyz.ksharma.krail.trip_planner.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import xyz.ksharma.krail.design.system.components.SeparatorIcon
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.design.system.toAdaptiveDecorativeIconSize
import xyz.ksharma.krail.design.system.toAdaptiveSize
import xyz.ksharma.krail.trip_planner.ui.R
import xyz.ksharma.krail.trip_planner.ui.state.TransportMode

/**
 * A card that displays information about a journey.
 * @param timeToDeparture The time until the journey departs.
 * @param originTime The time the journey starts.
 * @param destinationTime The time the journey ends.
 * @param totalTravelTime The total time the journey takes.
 * @param platformNumber The platform or stand number, the journey departs from.
 * @param isWheelchairAccessible Whether the journey is wheelchair accessible.
 * @param transportModeList The list of transport modes used in the journey.
 * @param onClick The action to perform when the card is clicked.
 * @param modifier The modifier to apply to the card.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JourneyCard(
    timeToDeparture: String,
    originTime: String,
    destinationTime: String,
    totalTravelTime: String,
    platformNumber: Char?=null,
    isWheelchairAccessible: Boolean,
    transportModeList: ImmutableList<TransportMode>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(color = KrailTheme.colors.surface)
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = if (transportModeList.size >= 2) {
                        transportModeList.map { it.colorCode.hexToComposeColor() }
                    } else {
                        val color = transportModeList.first().colorCode.hexToComposeColor()
                        listOf(color, color)
                    }),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(role = Role.Button, onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 12.dp),
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = timeToDeparture, style = KrailTheme.typography.titleMedium,
                color = transportModeList.first().colorCode.hexToComposeColor(),
                modifier = Modifier.padding(end = 8.dp).align(Alignment.CenterVertically)
            )
            Row(
                modifier = Modifier.align(Alignment.CenterVertically).weight(1f),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                transportModeList.forEachIndexed { index, mode ->
                    TransportModeIcon(
                        letter = mode.name.first(),
                        backgroundColor = mode.colorCode.hexToComposeColor(),
                    )
                    if (index != transportModeList.lastIndex) {
                        SeparatorIcon(modifier = Modifier.align(Alignment.CenterVertically))
                    }
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(28.dp.toAdaptiveDecorativeIconSize())
                    .border(
                        width = 3.dp,
                        color = transportModeList.first().colorCode.hexToComposeColor(),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = platformNumber.toString(),
                    textAlign = TextAlign.Center,
                    style = KrailTheme.typography.labelLarge,
                )
            }
        }

        Text(
            text = originTime,
            style = KrailTheme.typography.titleMedium,
            color = KrailTheme.colors.onSurface
        )

        FlowRow(
            horizontalArrangement = Arrangement.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        ) {
            Text(
                text = destinationTime,
                style = KrailTheme.typography.titleMedium,
                color = KrailTheme.colors.onSurface,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterVertically),
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_clock),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = KrailTheme.colors.onBackground),
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .align(Alignment.CenterVertically)
                        .size(14.dp.toAdaptiveSize()),
                )
                Text(
                    text = totalTravelTime, style = KrailTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (isWheelchairAccessible) {
                Image(
                    painter = painterResource(R.drawable.ic_a11y),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = KrailTheme.colors.onBackground),
                    modifier = Modifier
                        .size(14.dp.toAdaptiveSize())
                        .align(Alignment.CenterVertically),
                )
            }
        }
    }
}

// region Previews

@PreviewLightDark
@Preview(fontScale = 2f)
@Composable
private fun PreviewJourneyCard() {
    KrailTheme {
        JourneyCard(
            timeToDeparture = "in 5 mins",
            originTime = "8:25am",
            destinationTime = "8:40am",
            totalTravelTime = "15 mins",
            platformNumber = '1',
            isWheelchairAccessible = true,
            transportModeList = listOf(
                TransportMode.Train(),
                TransportMode.Bus()
            ).toImmutableList(),
            onClick = {},
        )
    }
}

@Preview
@Preview(fontScale = 2f)
@Composable
private fun PreviewJourneyCardLongData() {
    KrailTheme {
        JourneyCard(
            timeToDeparture = "in 1h 5mins",
            originTime = "12:25am",
            destinationTime = "12:40am",
            totalTravelTime = "45h 15minutes",
            platformNumber = 'A',
            isWheelchairAccessible = true,
            transportModeList = listOf(
                TransportMode.Ferry(),
                TransportMode.Bus(),
                TransportMode.Train(),
                TransportMode.Coach(),
                TransportMode.Metro(),
            ).toImmutableList(),
            onClick = {},
        )
    }
}

// endregion
