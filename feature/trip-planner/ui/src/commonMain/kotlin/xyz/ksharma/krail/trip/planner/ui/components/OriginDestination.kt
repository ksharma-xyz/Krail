package xyz.ksharma.krail.trip.planner.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.taj.components.Text
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.trip.planner.ui.state.timetable.Trip

@Composable
internal fun OriginDestination(
    trip: Trip,
    themeContentColor: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp, top = 4.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .timeLineTop(
                    color = themeContentColor,
                    strokeWidth = 3.dp,
                    circleRadius = 5.dp,
                )
                .padding(start = 12.dp, bottom = 8.dp),
        ) {
            AnimatedContent(
                targetState = trip.fromStopName,
                transitionSpec = {
                    (
                        fadeIn(
                            animationSpec = tween(200),
                        ) + slideInVertically(
                            initialOffsetY = { it / 2 },
                            animationSpec = tween(500, easing = EaseOutBounce),
                        )
                        ) togetherWith (
                        fadeOut(
                            animationSpec = tween(200),
                        ) + slideOutVertically(
                            targetOffsetY = { -it / 2 },
                            animationSpec = tween(500),
                        )
                        )
                },
                contentAlignment = Alignment.CenterStart,
                label = "originStopName",
            ) { targetText ->
                Text(
                    text = targetText,
                    color = themeContentColor,
                    style = KrailTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .timeLineBottom(
                    color = themeContentColor,
                    strokeWidth = 3.dp,
                    circleRadius = 5.dp,
                )
                .padding(start = 12.dp, top = 8.dp),
        ) {
            AnimatedContent(
                targetState = trip.toStopName,
                transitionSpec = {
                    (
                        fadeIn(
                            animationSpec = tween(200),
                        ) + slideInVertically(
                            initialOffsetY = { -it / 2 },
                            animationSpec = tween(500, easing = EaseOutBounce),
                        )
                        ) togetherWith (
                        fadeOut(
                            animationSpec = tween(200),
                        ) + slideOutVertically(
                            targetOffsetY = { it / 2 },
                            animationSpec = tween(500),
                        )
                        )
                },
                contentAlignment = Alignment.CenterStart,
                label = "destinationStopName",
            ) { targetText ->
                Text(
                    text = targetText,
                    color = themeContentColor,
                    style = KrailTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                )
            }
        }
    }
}
