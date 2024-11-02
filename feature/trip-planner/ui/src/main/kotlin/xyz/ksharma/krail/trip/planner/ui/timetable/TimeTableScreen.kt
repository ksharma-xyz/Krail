package xyz.ksharma.krail.trip.planner.ui.timetable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.components.TitleBar
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip.planner.ui.R
import xyz.ksharma.krail.trip.planner.ui.components.JourneyCard
import xyz.ksharma.krail.trip.planner.ui.components.JourneyCardState
import xyz.ksharma.krail.trip.planner.ui.components.hexToComposeColor
import xyz.ksharma.krail.trip.planner.ui.components.loading.LoadingEmojiAnim
import xyz.ksharma.krail.trip.planner.ui.components.timeLineBottom
import xyz.ksharma.krail.trip.planner.ui.components.timeLineTop
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.TransportModeLine
import xyz.ksharma.krail.trip.planner.ui.state.timetable.TimeTableState
import xyz.ksharma.krail.trip.planner.ui.state.timetable.TimeTableUiEvent
import xyz.ksharma.krail.trip.planner.ui.state.timetable.Trip
import xyz.ksharma.krail.design.system.R as DesignSystemR

@Composable
fun TimeTableScreen(
    timeTableState: TimeTableState,
    expandedJourneyId: String?,
    onEvent: (TimeTableUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    themeColor: Color = TransportMode.Train().colorCode.hexToComposeColor(), // TODO - theming
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = KrailTheme.colors.surface),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = themeColor),
        ) {
            TitleBar(
                title = {
                    Text(text = stringResource(R.string.time_table_screen_title))
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .clickable { onEvent(TimeTableUiEvent.SaveTripButtonClicked) },
                        contentAlignment = Alignment.Center,
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(
                                if (timeTableState.isTripSaved) {
                                    DesignSystemR.drawable.star_filled
                                } else {
                                    DesignSystemR.drawable.star_outline
                                },
                            ),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(KrailTheme.colors.onSurface),
                            modifier = Modifier.size(24.dp),
                        )
                    }
                },
            )
        }

        LazyColumn(contentPadding = PaddingValues(bottom = 16.dp)) {
            item {
                timeTableState.trip?.let { trip ->
                    OriginDestination(
                        trip,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = themeColor),
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (timeTableState.isLoading) {
                item {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        LoadingEmojiAnim(
                            modifier = Modifier
                                .padding(vertical = 60.dp)
                                .animateItem(),
                        )

                        Text(
                            text = "Hop on mate!",
                            style = KrailTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = KrailTheme.colors.onSurface,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                        )
                    }
                }
            } else if (timeTableState.journeyList.isNotEmpty()) {
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
                            .padding(horizontal = 12.dp, vertical = 8.dp),
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
}

@Composable
private fun OriginDestination(
    trip: Trip,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .timeLineTop(
                    color = KrailTheme.colors.onSurface,
                    strokeWidth = 3.dp,
                    circleRadius = 5.dp,
                )
                .padding(start = 12.dp, bottom = 8.dp),
        ) {
            Text(
                text = trip.fromStopName,
                style = KrailTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .timeLineBottom(
                    color = KrailTheme.colors.onSurface,
                    strokeWidth = 3.dp,
                    circleRadius = 5.dp,
                )
                .padding(start = 12.dp, top = 8.dp),
        ) {
            Text(
                text = trip.toStopName,
                style = KrailTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
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
            transportModeList = transportModeLineList.map { it.transportMode }
                .toImmutableList(),
            legList = legList,
            onClick = onClick,
            modifier = modifier,
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewOriginDestination() {
    KrailTheme {
        OriginDestination(
            modifier = Modifier.background(color = KrailTheme.colors.background),
            trip = Trip(
                fromStopName = "From Stop a really long stop name here, test multiline",
                toStopName = "To Stop",
                fromStopId = "123",
                toStopId = "456",
            ),
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewTimeTableScreen() {
    KrailTheme {
        TimeTableScreen(
            timeTableState = TimeTableState(
                trip = Trip(
                    fromStopName = "From Stop",
                    toStopName = "To Stop",
                    fromStopId = "123",
                    toStopId = "456",
                ),
                journeyList = listOf(
                    TimeTableState.JourneyCardInfo(
                        timeText = "12:00",
                        platformText = 'A',
                        originTime = "12:00",
                        destinationTime = "12:30",
                        travelTime = "30 mins",
                        transportModeLines = persistentListOf(
                            TransportModeLine(
                                transportMode = TransportMode.Bus(),
                                lineName = "123",
                            ),
                        ),
                        legs = persistentListOf(),
                        originUtcDateTime = "2024-11-01T12:00:00Z",
                    ),
                ).toImmutableList(),
            ),
            expandedJourneyId = null,
            onEvent = {},
        )
    }
}
