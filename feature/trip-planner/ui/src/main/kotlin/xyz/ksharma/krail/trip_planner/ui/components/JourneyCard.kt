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
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.LocalTextColor
import xyz.ksharma.krail.design.system.LocalTextStyle
import xyz.ksharma.krail.design.system.components.BasicJourneyCard
import xyz.ksharma.krail.design.system.components.Divider
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip_planner.ui.R

/**
 * A card that displays information about a journey.
 * @param timeToDeparture The time until the journey departs.
 * @param originTime The time the journey starts.
 * @param destinationTime The time the journey ends.
 * @param totalTravelTime The total time the journey takes.
 * @param platformNumber The platform or stand number, the journey departs from.
 * @param isWheelchairAccessible Whether the journey is wheelchair accessible.
 * @param transportModeIconRow A row of icons representing the transport modes used in the journey.
 * @param onClick The action to perform when the card is clicked.
 * @param modifier The modifier to apply to the card.
 */
@Composable
fun JourneyCard(
    timeToDeparture: String,
    originTime: String,
    destinationTime: String,
    totalTravelTime: String,
    platformNumber: Char,
    isWheelchairAccessible: Boolean,
    transportModeIconRow: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = timeToDeparture, style = KrailTheme.typography.titleMedium,
                color = "#127766".hexToComposeColor()
            )
            Text(
                text = originTime,
                style = KrailTheme.typography.titleMedium,
                color = KrailTheme.colors.onSurface
            )
            Box(
                modifier = Modifier
                    .background(
                        color = KrailTheme.colors.primary,
                        shape = RectangleShape
                    )
                //.siz,//"#9F9F9F".hexToComposeColor())
            )
            Text(
                text = destinationTime,
                style = KrailTheme.typography.titleMedium,
                color = KrailTheme.colors.onSurface,
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            transportModeIconRow()
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_clock),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = KrailTheme.colors.onBackground),

                )
                Text(text = totalTravelTime, style = KrailTheme.typography.bodyMedium)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), horizontalArrangement = Arrangement.End
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .border(2.dp, KrailTheme.colors.onBackground, shape = CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = platformNumber.toString(),
                        textAlign = TextAlign.Center,
                    )
                }
                if (isWheelchairAccessible) {
                    Image(
                        painter = painterResource(R.drawable.ic_a11y),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = KrailTheme.colors.onBackground),
                        modifier = Modifier.size(24.dp),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
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
            transportModeIconRow = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TransportModeIcon(
                        letter = 'T',
                        backgroundColor = "#F59A24".hexToComposeColor(),
                    )
                    TransportModeIcon(
                        letter = 'B',
                        backgroundColor = "#00B5EF".hexToComposeColor(),
                    )
                }
            },
            onClick = {},
        )
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JourneyCard(
    departureTimeText: String,
    departureLocationText: String? = null,
    originAndDestinationTimeText: String,
    durationText: String,
    backgroundColor: Color = KrailTheme.colors.surface,
    transportModeIconRow: @Composable RowScope.() -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicJourneyCard(
        modifier = modifier.clickable(role = Role.Button, onClick = onClick),
        backgroundColor = backgroundColor,
        content = {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                maxItemsInEachRow = 1,
            ) {
                CompositionLocalProvider(
                    LocalTextStyle provides KrailTheme.typography.titleSmall,
                    LocalTextColor provides KrailTheme.colors.secondary,
                ) {
                    Text(
                        text = departureTimeText,
                        color = LocalTextColor.current,
                        modifier = Modifier
                            .alignByBaseline()
                            .padding(end = 8.dp),
                    )
                }
                if (departureLocationText != null) {
                    CompositionLocalProvider(
                        LocalTextStyle provides KrailTheme.typography.labelLarge,
                        LocalTextColor provides KrailTheme.colors.onBackground,
                    ) {
                        Text(
                            text = departureLocationText,
                            color = LocalTextColor.current,
                            modifier = Modifier.alignByBaseline(),
                        )
                    }
                }
            }
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
            ) {
                CompositionLocalProvider(
                    LocalTextStyle provides KrailTheme.typography.labelLarge,
                    LocalTextColor provides KrailTheme.colors.onBackground,
                ) {
                    Text(
                        text = originAndDestinationTimeText,
                        color = LocalTextColor.current,
                        modifier = Modifier
                            .alignByBaseline()
                            .padding(end = 8.dp),
                    )
                }
                CompositionLocalProvider(
                    LocalTextStyle provides KrailTheme.typography.bodyMedium,
                    LocalTextColor provides KrailTheme.colors.secondary,
                ) {
                    Text(
                        text = durationText,
                        color = LocalTextColor.current,
                        modifier = Modifier.alignByBaseline(),
                    )
                }
            }
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                transportModeIconRow()
            }
        },
    )
}

// region Previews
/**
@Preview
@Preview(fontScale = 2f)
@Composable
private fun JourneyCardTrainLongTextPreview() {
KrailTheme {
JourneyCard(
departureTimeText = "in 2h 3mins",
departureLocationText = "on Parramatta Station, Stand A",
originAndDestinationTimeText = "8:25am - 8:40am",
durationText = "23 mins",
transportModeIconRow = {
TransportModeInfo(
letter = 'T',
backgroundColor = "#005aa3".hexToComposeColor(),
badgeColor = "#005aa3".hexToComposeColor(),
badgeText = "T4",
)
SeparatorIcon(modifier = Modifier.align(Alignment.CenterVertically))
TransportModeInfo(
letter = 'T',
backgroundColor = "#005aa3".hexToComposeColor(),
badgeText = "T4",
)
SeparatorIcon(modifier = Modifier.align(Alignment.CenterVertically))
TransportModeInfo(
letter = 'T',
backgroundColor = "#005aa3".hexToComposeColor(),
badgeText = "T4",
)
SeparatorIcon(modifier = Modifier.align(Alignment.CenterVertically))
TransportModeInfo(
letter = 'T',
backgroundColor = "#005aa3".hexToComposeColor(),
badgeText = "T4",
)
SeparatorIcon(modifier = Modifier.align(Alignment.CenterVertically))
TransportModeInfo(
letter = 'T',
backgroundColor = "#005aa3".hexToComposeColor(),
badgeText = "T4",
)
},
onClick = {}
)
}
}

@ComponentPreviews
@Composable
private fun JourneyCardMultipleModesPreview() {
KrailTheme {
JourneyCard(
departureTimeText = "in 5 mins",
departureLocationText = "on Platform 1",
originAndDestinationTimeText = "8:25am - 8:40am",
durationText = "15 mins",
transportModeIconRow = {
TransportModeInfo(
letter = 'T',
backgroundColor = "#005aa3".hexToComposeColor(),
badgeText = "T4",
)
SeparatorIcon(modifier = Modifier.align(Alignment.CenterVertically))
TransportModeInfo(
letter = 'B',
backgroundColor = "#00B5EF".hexToComposeColor(),
badgeText = "700",
)
},
onClick = {},
)
}
}

// endregion
 */
