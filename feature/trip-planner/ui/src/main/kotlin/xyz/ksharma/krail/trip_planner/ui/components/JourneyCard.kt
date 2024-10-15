package xyz.ksharma.krail.trip_planner.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.LocalTextColor
import xyz.ksharma.krail.design.system.LocalTextStyle
import xyz.ksharma.krail.design.system.components.BasicJourneyCard
import xyz.ksharma.krail.design.system.components.SeparatorIcon
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.preview.ComponentPreviews
import xyz.ksharma.krail.design.system.theme.KrailTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JourneyCard(
    departureTimeText: String,
    departureLocationText: String? = null,
    originAndDestinationTimeText: String,
    durationText: String,
    backgroundColor: Color = KrailTheme.colors.tertiaryContainer.copy(alpha = 0.8f),
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
