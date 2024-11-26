package xyz.ksharma.krail.trip.planner.ui.timetable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import krail.feature.trip_planner.ui.generated.resources.Res
import krail.feature.trip_planner.ui.generated.resources.ic_reverse
import krail.feature.trip_planner.ui.generated.resources.ic_star
import krail.feature.trip_planner.ui.generated.resources.ic_star_filled
import org.jetbrains.compose.resources.painterResource
import xyz.ksharma.krail.taj.LocalThemeColor
import xyz.ksharma.krail.taj.components.Text
import xyz.ksharma.krail.taj.components.TitleBar
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.trip.planner.ui.components.ActionData
import xyz.ksharma.krail.trip.planner.ui.components.ErrorMessage
import xyz.ksharma.krail.trip.planner.ui.components.JourneyCard
import xyz.ksharma.krail.trip.planner.ui.components.JourneyCardState
import xyz.ksharma.krail.trip.planner.ui.components.OriginDestination
import xyz.ksharma.krail.trip.planner.ui.components.loading.LoadingEmojiAnim
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import xyz.ksharma.krail.trip.planner.ui.state.TransportModeLine
import xyz.ksharma.krail.trip.planner.ui.state.timetable.TimeTableState
import xyz.ksharma.krail.trip.planner.ui.state.timetable.TimeTableUiEvent
import xyz.ksharma.krail.trip.planner.ui.state.timetable.Trip

@Composable
fun TimeTableScreen(
    timeTableState: TimeTableState,
    expandedJourneyId: String?,
    onEvent: (TimeTableUiEvent) -> Unit,
    onAlertClick: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = KrailTheme.colors.surface),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            TitleBar(
                navAction = {
                    ActionButton(
                        onClick = onBackClick,
                        contentDescription = "Back",
                    ) {
                        Image(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(KrailTheme.colors.onSurface),
                            modifier = Modifier.size(24.dp),
                        )
                    }
                },
                title = {
                    Text(
                        text = "TimeTable",
                        color = KrailTheme.colors.onSurface,
                    )
                },
                actions = {
                    ActionButton(
                        onClick = {
                            onEvent(TimeTableUiEvent.ReverseTripButtonClicked)
                        },
                        contentDescription = "Reverse Trip Search",
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.ic_reverse),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(KrailTheme.colors.onSurface),
                            modifier = Modifier.size(24.dp),
                        )
                    }
                    ActionButton(
                        onClick = { onEvent(TimeTableUiEvent.SaveTripButtonClicked) },
                        contentDescription = if (timeTableState.isTripSaved) {
                            "Remove Saved Trip"
                        } else {
                            "Save Trip"
                        },
                    ) {
                        Image(
                            painter = if (timeTableState.isTripSaved) {
                                painterResource(Res.drawable.ic_star_filled)
                            } else {
                                painterResource(Res.drawable.ic_star)
                            },
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
                        trip = trip,
                        timeLineColor = KrailTheme.colors.onSurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .background(color = KrailTheme.colors.surface),
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (timeTableState.isError) {
                item {
                    ErrorMessage(
                        title = "Eh! That's not looking right mate!",
                        message = "Let's try again.",
                        actionData = ActionData(
                            actionText = "Retry",
                            onActionClick = { onEvent(TimeTableUiEvent.RetryButtonClicked) },
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItem(),
                    )
                }
            } else if (timeTableState.isLoading) {
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
                        platformNumber = journey.platformNumber,
                        platformText = journey.platformText,
                        originTime = journey.originTime,
                        destinationTime = journey.destinationTime,
                        durationText = journey.travelTime,
                        totalWalkTime = journey.totalWalkTime,
                        transportModeLineList = journey.transportModeLines.map {
                            TransportModeLine(
                                transportMode = it.transportMode,
                                lineName = it.lineName,
                            )
                        }.toImmutableList(),
                        legList = journey.legs.toImmutableList(),
                        cardState = if (expandedJourneyId == journey.journeyId) {
                            JourneyCardState.EXPANDED
                        } else {
                            JourneyCardState.DEFAULT
                        },
                        onClick = {
                            onEvent(TimeTableUiEvent.JourneyCardClicked(journey.journeyId))
                        },
                        totalUniqueServiceAlerts = journey.totalUniqueServiceAlerts,
                        onAlertClick = {
                            onAlertClick(journey.journeyId)
                        },
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                    )
                }
            } else { // Journey list is empty or null
                item {
                    ErrorMessage(
                        title = "No route found!",
                        message = "Search for another stop or check back later.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItem(),
                    )
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

@Composable // todo - probably don't need this
private fun JourneyCardItem(
    timeToDeparture: String,
    platformNumber: String?,
    platformText: String?,
    originTime: String,
    durationText: String,
    totalWalkTime: String?,
    destinationTime: String,
    onClick: () -> Unit,
    cardState: JourneyCardState,
    legList: ImmutableList<TimeTableState.JourneyCardInfo.Leg>,
    onAlertClick: () -> Unit,
    totalUniqueServiceAlerts: Int,
    modifier: Modifier = Modifier,
    transportModeLineList: ImmutableList<TransportModeLine>? = null,
) {
    if (!transportModeLineList.isNullOrEmpty() && legList.isNotEmpty()) {
        JourneyCard(
            timeToDeparture = timeToDeparture,
            originTime = originTime,
            destinationTime = destinationTime,
            totalTravelTime = durationText,
            platformNumber = platformNumber,
            platformText = platformText,
            isWheelchairAccessible = false,
            cardState = cardState,
            transportModeList = transportModeLineList.map { it.transportMode }
                .toImmutableList(),
            legList = legList,
            totalWalkTime = totalWalkTime,
            onClick = onClick,
            onAlertClick = onAlertClick,
            totalUniqueServiceAlerts = totalUniqueServiceAlerts,
            modifier = modifier,
        )
    }
}

@Composable
fun ActionButton(
    onClick: () -> Unit,
    contentDescription: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .clickable(role = Role.Button) { onClick() }
            .semantics(mergeDescendants = true) {
                this.contentDescription = contentDescription
            },
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

// region Preview

@Composable
private fun PreviewTimeTableScreen() {
    KrailTheme {
        val themeColor = remember { mutableStateOf(TransportMode.Ferry().colorCode) }
        CompositionLocalProvider(LocalThemeColor provides themeColor) {
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
                            platformText = "Stand A",
                            platformNumber = "A",
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
                            totalUniqueServiceAlerts = 3,
                            originUtcDateTime = "2024-11-01T12:00:00Z",
                        ),
                    ).toImmutableList(),
                ),
                expandedJourneyId = null,
                onEvent = {},
                onAlertClick = {},
                onBackClick = {},
            )
        }
    }
}

@Composable
private fun PreviewTimeTableScreenError() {
    KrailTheme {
        val themeColor = remember { mutableStateOf(TransportMode.Train().colorCode) }
        CompositionLocalProvider(LocalThemeColor provides themeColor) {
            TimeTableScreen(
                timeTableState = TimeTableState(
                    trip = Trip(
                        fromStopName = "From Stop",
                        toStopName = "To Stop",
                        fromStopId = "123",
                        toStopId = "456",
                    ),
                    isError = true,
                    isLoading = false,
                ),
                expandedJourneyId = null,
                onEvent = {},
                onAlertClick = {},
                onBackClick = {},
            )
        }
    }
}

@Composable
private fun PreviewTimeTableScreenNoResults() {
    KrailTheme {
        val themeColor = remember { mutableStateOf(TransportMode.Train().colorCode) }
        CompositionLocalProvider(LocalThemeColor provides themeColor) {
            TimeTableScreen(
                timeTableState = TimeTableState(
                    trip = Trip(
                        fromStopName = "From Stop",
                        toStopName = "To Stop",
                        fromStopId = "123",
                        toStopId = "456",
                    ),
                    isError = false,
                    isLoading = false,
                ),
                expandedJourneyId = null,
                onEvent = {},
                onAlertClick = {},
                onBackClick = {},
            )
        }
    }
}

// endregion
