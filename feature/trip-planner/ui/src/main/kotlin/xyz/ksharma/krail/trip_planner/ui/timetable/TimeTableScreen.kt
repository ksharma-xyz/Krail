package xyz.ksharma.krail.trip_planner.ui.timetable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import xyz.ksharma.krail.trip_planner.ui.R
import xyz.ksharma.krail.trip_planner.ui.components.JourneyCard
import xyz.ksharma.krail.trip_planner.ui.components.JourneyDetailCard
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
            .background(color = KrailTheme.colors.background),
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
            item {
                Text(
                    text = if (timeTableState.isTripSaved) "Trip Saved" else "Save Trip Button",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .clickable(enabled = !timeTableState.isTripSaved) { onEvent(TimeTableUiEvent.SaveTripButtonClicked) }
                )
            }

            items(timeTableState.journeyList) { journey ->

                AnimatedVisibility(
                    visible = expandedJourneyId == journey.journeyId,
                    enter = scaleIn(initialScale = 0.3f) + fadeIn(),
                    exit = fadeOut() + shrinkOut(),
                ) {
                    JourneyDetailCard(
                        timeToDeparture = journey.timeText,
                        platformNumber = journey.platformText ?: ' ',
                        totalTravelTime = journey.travelTime,
                        legList = journey.legs.toImmutableList(),
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
                        timeToDeparture = journey.timeText,
                        departureLocationNumber = journey.platformText,
                        originTime = journey.originTime,
                        destinationTime = journey.destinationTime,
                        durationText = journey.travelTime,
                        transportModeLineList = journey.transportModeLines?.map {
                            TransportModeLine(
                                transportMode = it.transportMode,
                                lineName = it.lineName,
                            )
                        }?.toImmutableList(),
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
    timeToDeparture: String,
    departureLocationNumber: Char?,
    originTime: String,
    durationText: String,
    destinationTime: String,
    transportModeLineList: ImmutableList<TransportModeLine>? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    JourneyCard(
        timeToDeparture = timeToDeparture,
        originTime = originTime,
        destinationTime = destinationTime,
        totalTravelTime = durationText,
        platformNumber = departureLocationNumber,
        isWheelchairAccessible = false,
        transportModeList = transportModeLineList.takeIf { it?.isNotEmpty() == true }
            ?.map { it.transportMode }?.toImmutableList(),
        onClick = onClick,
        modifier = modifier,
    )
}
