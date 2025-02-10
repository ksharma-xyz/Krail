package xyz.ksharma.krail.trip.planner.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.taj.LocalTextColor
import xyz.ksharma.krail.taj.LocalTextStyle
import xyz.ksharma.krail.taj.components.Text
import xyz.ksharma.krail.taj.hexToComposeColor
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.taj.toAdaptiveDecorativeIconSize
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode

@Composable
fun TransportModeChip(
    transportMode: TransportMode,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) transportMode.colorCode.hexToComposeColor()
        else KrailTheme.colors.surface,
        animationSpec = tween(200),
    )

    val textColor by animateColorAsState(
        targetValue = if (selected) Color.White else Color.Gray, // gray needs to come from token
        animationSpec = tween(200),
    )

    val borderColor by animateColorAsState(
        targetValue = if (selected) Color.Transparent else transportMode.colorCode.hexToComposeColor(),
        animationSpec = tween(200),
    )

    val modeIconBorderColor by animateColorAsState(
        targetValue = if (selected) Color.White else KrailTheme.colors.surface,
        animationSpec = tween(200),
    )

    CompositionLocalProvider(
        LocalTextColor provides textColor,
        LocalTextStyle provides KrailTheme.typography.titleMedium,
    ) {
        Row(
            modifier = modifier
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(50),
                )
                .border(
                    width = 3.dp, // 3.dp is token , should be 1.dp less than the horizontal padding.
                    color = borderColor,
                    shape = RoundedCornerShape(50),
                )
                .clickable(
                    indication = null,
                    interactionSource = null,
                ) { onClick() }
                .padding(horizontal = 4.dp, vertical = 4.dp) // 4.dp is token
                .padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            // Make this a separate component - TransportModeIcon
            Box(
                modifier = Modifier
                    .size(32.dp.toAdaptiveDecorativeIconSize())
                    .clip(CircleShape)
                    .background(
                        color = transportMode.colorCode.hexToComposeColor(),
                        shape = CircleShape,
                    )
                    .border(
                        3.dp.toAdaptiveDecorativeIconSize(), // 3.dp is token value
                        modeIconBorderColor,
                        CircleShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                CompositionLocalProvider(
                    LocalTextColor provides Color.White,
                ) {
                    Text(
                        text = transportMode.name.first().toString(),
                        color = Color.White,
                    )
                }
            }

            Text(text = transportMode.name)
        }
    }
}
