package xyz.ksharma.krail.trip_planner.ui.timetable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import xyz.ksharma.krail.design.system.components.SeparatorIcon
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.components.TitleBar
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip_planner.ui.R
import xyz.ksharma.krail.trip_planner.ui.components.JourneyCard
import xyz.ksharma.krail.trip_planner.ui.components.TransportModeInfo
import xyz.ksharma.krail.trip_planner.ui.components.hexToComposeColor
import xyz.ksharma.krail.trip_planner.ui.state.TransportModeLine
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableState

@Composable
fun TimeTableScreen(
    timeTableState: TimeTableState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .background(color = KrailTheme.colors.background)
            .systemBarsPadding(),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            TitleBar(title = {
                Text(text = stringResource(R.string.time_table_screen_title))
            })
        }

        if (timeTableState.isLoading) {
            item {
                Text("Loading...", modifier = Modifier.padding(horizontal = 16.dp))
            }
        } else if (timeTableState.journeyList.isNotEmpty()) {
            items(timeTableState.journeyList) { journey ->
                JourneyCardItem(
                    departureText = journey.timeText + if (journey.platformText != null) {
                        " on ${journey.platformText}"
                    } else "",
                    timeText = journey.originTime + " - " + journey.destinationTime + " (${journey.travelTime})",
                    transportModeLineList = journey.transportModeLines.map {
                        TransportModeLine(
                            transportMode = it.transportMode,
                            lineName = it.lineName,
                        )
                    }.toImmutableList(),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                )
            }
        } else {
            item {
                Text("No data found")
            }
        }
    }
}

@Composable
fun JourneyCardItem(
    departureText: String,
    timeText: String,
    transportModeLineList: ImmutableList<TransportModeLine>,
    modifier: Modifier = Modifier,
) {
    JourneyCard(
        modifier = modifier,
        departureText = {
            Text(
                text = departureText,
                color = KrailTheme.colors.onSecondaryContainer,
            )
        },
        timeText = {
            Text(
                text = timeText,
                color = KrailTheme.colors.onSecondaryContainer,
                modifier = Modifier.alignByBaseline(),
            )
        },
        transportModeIconRow = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                transportModeLineList.forEachIndexed { index, line ->
                    TransportModeInfo(
                        letter = line.transportMode.name.first(),
                        backgroundColor = line.transportMode.colorCode.hexToComposeColor(),
                        badgeText = line.lineName,
                    )
                    if (index < transportModeLineList.size - 1) SeparatorIcon()
                }
            }
        }
    )
}
