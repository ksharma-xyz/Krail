package xyz.ksharma.krail.trip_planner.ui.timetable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import xyz.ksharma.krail.trip_planner.ui.components.JourneyDetailCard
import xyz.ksharma.krail.trip_planner.ui.components.TransportModeInfo
import xyz.ksharma.krail.trip_planner.ui.components.hexToComposeColor
import xyz.ksharma.krail.trip_planner.ui.state.TransportModeLine
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableState
import xyz.ksharma.krail.trip_planner.ui.state.timetable.TimeTableUiEvent

@Composable
fun TimeTableScreen(
    timeTableState: TimeTableState,
    expandedJourneyId: String?,
    onEvent: (TimeTableUiEvent) -> Unit,
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
                var isFirstTime by rememberSaveable { mutableStateOf(true) }

                AnimatedVisibility(
                    visible = expandedJourneyId == journey.journeyId,
                    enter = scaleIn(initialScale = 0.3f) + fadeIn(),
                    exit = fadeOut() + shrinkOut(),
                ) {
                    JourneyDetailCard(
                        header = journey.timeText + journey.platformText?.let { " on ${journey.platformText}" } + " (${journey.travelTime})",
                        journeyLegList = journey.legs,
                        onClick = {
                            onEvent(TimeTableUiEvent.JourneyCardClicked(journey.journeyId))
                        },
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .animateContentSize(),
                    )
                }
                AnimatedVisibility(
                    visible = expandedJourneyId != journey.journeyId,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    JourneyCardItem(
                        departureTimeText = journey.timeText,
                        departureLocationText = journey.platformText?.let { "on ${journey.platformText}" },
                        originDestinationTimeText = journey.originTime + " - " + journey.destinationTime,
                        durationText = journey.travelTime,
                        transportModeLineList = journey.transportModeLines.map {
                            TransportModeLine(
                                transportMode = it.transportMode,
                                lineName = it.lineName,
                            )
                        }.toImmutableList(),
                        onClick = {
                            onEvent(TimeTableUiEvent.JourneyCardClicked(journey.journeyId))
                        },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    )
                }
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
    departureTimeText: String,
    departureLocationText: String? = null,
    originDestinationTimeText: String,
    durationText: String,
    transportModeLineList: ImmutableList<TransportModeLine>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    JourneyCard(
        modifier = modifier,
        departureTimeText = departureTimeText,
        departureLocationText = departureLocationText,
        originAndDestinationTimeText = originDestinationTimeText,
        durationText = durationText,
        transportModeIconRow = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                transportModeLineList.forEachIndexed { index, line ->
                    TransportModeInfo(
                        letter = line.transportMode.name.first(),
                        backgroundColor = line.transportMode.colorCode.hexToComposeColor(),
                        badgeColor = line.lineColorCode?.hexToComposeColor(),
                        badgeText = line.lineName,
                    )
                    if (index < transportModeLineList.size - 1) SeparatorIcon()
                }
            }
        },
        onClick = onClick,
    )
}
