package xyz.ksharma.krail.trip_planner.ui.timetable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import xyz.ksharma.krail.design.system.components.JourneyCard
import xyz.ksharma.krail.design.system.components.SeparatorIcon
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.components.TitleBar
import xyz.ksharma.krail.design.system.components.TransportModeInfo
import xyz.ksharma.krail.design.system.model.TransportModeLine
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip_planner.ui.R
import xyz.ksharma.krail.trip_planner.ui.components.toDisplayModeType
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableState
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableUiEvent

@Composable
fun TimeTableScreen(
    timeTableState: TimeTableState,
    modifier: Modifier = Modifier,
    onEvent: (TimeTableUiEvent) -> Unit = {},
) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues()) {
        item {
            TitleBar(title = stringResource(R.string.time_table_screen_title))
        }

        if (timeTableState.isLoading) {
            item {
                Text("Loading...", modifier = Modifier.padding(horizontal = 16.dp))
            }
        } else if (timeTableState.journeyList.isNotEmpty()) {
            items(timeTableState.journeyList) { journey ->
                JourneyCardItem(
                    departureText = "in " + journey.timeText + " on " + journey.platformText,
                    timeText = journey.originTime + " - " + journey.destinationTime + " (${journey.travelTime})",
                    transportModeLineList = journey.transportModeLines.map {
                        val displayModeType = it.transportModeType.toDisplayModeType()
                        TransportModeLine(
                            transportModeType = displayModeType,
                            lineName = it.lineName,
                            lineHexColorCode = displayModeType.hexColorCode
                        )
                    }.toImmutableList(),
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }
        else {
            item {
                Text("No data found")
            }
        }
    }
}
/*
private fun TimeTableState.TransportModeLine.toDisplayModel() = TransportModeLine(
    transportModeType = transportModeType.toDisplayModeType(),
    lineName = lineName,
    lineHexColorCode = lineHexColorCode
)*/

@Composable
fun JourneyCardItem(
    departureText: String,
    timeText: String,
    transportModeLineList: ImmutableList<TransportModeLine>,
    modifier: Modifier = Modifier,
) {
    JourneyCard(
        modifier = modifier
            .background(KrailTheme.colors.secondaryContainer)
            .padding(horizontal = 16.dp),
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
                    TransportModeInfo(transportModeLine = line)
                    if (index < transportModeLineList.size - 1) SeparatorIcon()
                }
            }
        })
}
