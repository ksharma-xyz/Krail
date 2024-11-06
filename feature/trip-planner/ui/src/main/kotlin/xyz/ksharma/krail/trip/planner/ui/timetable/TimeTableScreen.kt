package xyz.ksharma.krail.trip.planner.ui.timetable

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import xyz.ksharma.krail.design.system.LocalThemeColor
import xyz.ksharma.krail.design.system.LocalThemeContentColor
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.components.TitleBar
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.design.system.theme.shouldUseDarkIcons
import xyz.ksharma.krail.trip.planner.ui.R
import xyz.ksharma.krail.trip.planner.ui.components.JourneyCard
import xyz.ksharma.krail.trip.planner.ui.components.JourneyCardState
import xyz.ksharma.krail.trip.planner.ui.components.OriginDestination
import xyz.ksharma.krail.trip.planner.ui.components.hexToComposeColor
import xyz.ksharma.krail.trip.planner.ui.components.loading.LoadingEmojiAnim
import xyz.ksharma.krail.trip.planner.ui.getActivityOrNull
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
) {
    val themeColorHex by LocalThemeColor.current
    val themeContentColorHex by LocalThemeContentColor.current
    val themeColor by remember { mutableStateOf(themeColorHex.hexToComposeColor()) }
    val themeContentColor by remember { mutableStateOf(themeContentColorHex.hexToComposeColor()) }
    val context = LocalContext.current
    val darkIcons = shouldUseDarkIcons(themeColor)
    DisposableEffect(darkIcons) {
        context.getActivityOrNull()?.let { activity ->
            (activity as ComponentActivity).enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(
                    android.graphics.Color.TRANSPARENT,
                    android.graphics.Color.TRANSPARENT,
                ) { darkIcons },
                navigationBarStyle = SystemBarStyle.auto(
                    lightScrim = themeContentColor.toArgb(),
                    darkScrim = themeContentColor.toArgb(),
                ),
            )
        }
        onDispose {}
    }

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
                    Text(
                        text = stringResource(R.string.time_table_screen_title),
                        color = themeContentColor,
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
                            imageVector = ImageVector.vectorResource(R.drawable.ic_reverse),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(themeContentColor),
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
                            imageVector = ImageVector.vectorResource(
                                if (timeTableState.isTripSaved) {
                                    DesignSystemR.drawable.star_filled
                                } else {
                                    DesignSystemR.drawable.star_outline
                                },
                            ),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(themeContentColor),
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
                        themeContentColor = themeContentColor,
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
                        totalWalkTime = journey.totalWalkTime,
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
fun JourneyCardItem(
    timeToDeparture: String,
    departureLocationNumber: Char?,
    originTime: String,
    durationText: String,
    totalWalkTime: String?,
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
            totalWalkTime = totalWalkTime,
            onClick = onClick,
            modifier = modifier,
        )
    }
}

@PreviewLightDark
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
