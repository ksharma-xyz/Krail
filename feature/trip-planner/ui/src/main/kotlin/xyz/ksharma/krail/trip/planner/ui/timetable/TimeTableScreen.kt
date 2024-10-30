package xyz.ksharma.krail.trip.planner.ui.timetable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import xyz.ksharma.krail.design.system.LocalOnContentColor
import xyz.ksharma.krail.design.system.components.RoundIconButton
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.components.TitleBar
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.trip.planner.ui.R
import xyz.ksharma.krail.trip.planner.ui.components.JourneyCard
import xyz.ksharma.krail.trip.planner.ui.components.JourneyCardState
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = KrailTheme.colors.surface),
    ) {
        TitleBar(
            title = {
                Text(text = stringResource(R.string.time_table_screen_title))
            },
            actions = {
                RoundIconButton(
                    onClick = { onEvent(TimeTableUiEvent.SaveTripButtonClicked) },
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
                        colorFilter = ColorFilter.tint(LocalOnContentColor.current),
                    )
                }
            },
        )

        timeTableState.trip?.let { trip ->
            JourneyRoute(trip)
        }

        LazyColumn(contentPadding = PaddingValues(vertical = 16.dp)) {
            if (timeTableState.isLoading) {
                item {
                    Text("Loading...", modifier = Modifier.padding(horizontal = 16.dp))
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
                            .padding(horizontal = 16.dp, vertical = 8.dp),
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
private fun JourneyRoute(trip: Trip) {
    val (showFirstText, setShowFirstText) = remember { mutableStateOf(false) }
    val (showSecondText, setShowSecondText) = remember { mutableStateOf(false) }
    val textStyle = KrailTheme.typography.titleMedium

    LaunchedEffect(Unit) {
        setShowFirstText(true)
        delay(500) // Delay for half a second before showing the second text
        setShowSecondText(true)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 20.dp)
            .clip(RoundedCornerShape(8.dp)),
    ) {
        val density = LocalDensity.current

        Row(Modifier.height(with(density) { textStyle.fontSize.toDp() })) {
            AnimatedVisibility(
                visible = showFirstText,
                enter = fadeIn(animationSpec = tween(500)) + slideInVertically(
                    animationSpec = tween(
                        600,
                    ),
                ) { it },
            ) {
                Text(
                    text = trip.fromStopName,
                    style = textStyle.copy(fontWeight = FontWeight.Normal),
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(Modifier.height(with(density) { textStyle.fontSize.toDp() })) {
            AnimatedVisibility(
                visible = showSecondText,
                enter = fadeIn(animationSpec = tween(500)) + slideInVertically(
                    animationSpec = tween(
                        600,
                    ),
                ) { it },
            ) {
                Text(
                    text = trip.toStopName,
                    style = textStyle.copy(fontWeight = FontWeight.Normal),
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
