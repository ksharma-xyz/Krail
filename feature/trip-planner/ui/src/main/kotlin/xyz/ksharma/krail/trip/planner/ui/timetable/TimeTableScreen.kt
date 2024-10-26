package xyz.ksharma.krail.trip.planner.ui.timetable

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.components.TitleBar
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip.planner.ui.R
import xyz.ksharma.krail.trip.planner.ui.components.JourneyCard
import xyz.ksharma.krail.trip.planner.ui.components.JourneyCardState
import xyz.ksharma.krail.trip.planner.ui.state.TransportModeLine
import xyz.ksharma.krail.trip.planner.ui.state.timetable.TimeTableState
import xyz.ksharma.krail.trip.planner.ui.state.timetable.TimeTableUiEvent

@Composable
fun TimeTableScreen(
    timeTableState: TimeTableState,
    expandedJourneyId: String?,
    onEvent: (TimeTableUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .background(color = KrailTheme.colors.background),
        contentPadding = PaddingValues(vertical = 16.dp),
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
            item {
                Text(
                    text = if (timeTableState.isTripSaved) "Trip Saved" else "Save Trip Button",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .clickable(
                            enabled = !timeTableState.isTripSaved,
                        ) { onEvent(TimeTableUiEvent.SaveTripButtonClicked) },
                )
            }

            items(timeTableState.journeyList) { journey ->
                JourneyCardItem(
                    timeToDeparture = journey.timeText,
                    departureLocationNumber = journey.platformText,
                    originTime = journey.originTime,
                    destinationTime = journey.destinationTime,
                    durationText = journey.travelTime,
                    transportModeLineList = journey.transportModeLines.map {
                        TransportModeLine(
                            transportMode = it.transportMode,
                            lineName = it.lineName,
                        )
                    }.toImmutableList(),
                    legList = journey.legs.toImmutableList(),
                    cardState = if (expandedJourneyId == journey.journeyId) {
                        JourneyCardState.COLLAPSED
                    } else {
                        JourneyCardState.DEFAULT
                    },
                    onClick = {
                        onEvent(TimeTableUiEvent.JourneyCardClicked(journey.journeyId))
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .animateContentSize(),
                )
            }
        } else {
            item {
                Text("No data found")
            }
        }

        item {
            Spacer(
                modifier = Modifier
                    .height(96.dp)
                    .systemBarsPadding(),
            )
        }
    }
}

@Composable
fun JourneyCardItem(
    timeToDeparture: String,
    departureLocationNumber: Char?,
    originTime: String,
    durationText: String,
    destinationTime: String,
    onClick: () -> Unit,
    cardState: JourneyCardState,
    legList: ImmutableList<TimeTableState.JourneyCardInfo.Leg>,
    modifier: Modifier = Modifier,
    transportModeLineList: ImmutableList<TransportModeLine>? = null,
) {
    if (!transportModeLineList.isNullOrEmpty() && legList.isNotEmpty()) {
        JourneyCard(
            timeToDeparture = timeToDeparture,
            originTime = originTime,
            destinationTime = destinationTime,
            totalTravelTime = durationText,
            platformNumber = departureLocationNumber,
            isWheelchairAccessible = false,
            cardState = cardState,
            transportModeList = transportModeLineList.map { it.transportMode }.toImmutableList(),
            legList = legList,
            onClick = onClick,
            modifier = modifier,
        )
    }
}
