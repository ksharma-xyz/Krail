package xyz.ksharma.krail.trip.planner.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import xyz.ksharma.krail.taj.components.Text
import xyz.ksharma.krail.taj.theme.KrailTheme

@Composable
fun RadioButton(
    text: String,
    themeColor: Color,
    type: RadioButtonType = RadioButtonType.DEFAULT,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {
    val animationProgress by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = tween(durationMillis = 250)
    )

    val borderThickness = lerp(start = 0.dp, stop = 4.dp, fraction = animationProgress)
    val animatedBackgroundColor =
        androidx.compose.ui.graphics.lerp(
            start = Color.Transparent,
            stop = themeColor,
            fraction = animationProgress,
        )

    Box(
        modifier = modifier
            .height(
                when (type) {
                    RadioButtonType.SMALL -> 32.dp
                    RadioButtonType.DEFAULT -> 48.dp
                }
            )
            .clip(shape = RoundedCornerShape(8.dp))
            .background(
                color = animatedBackgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = borderThickness, // Dynamic border thickness
                color = themeColor, // Border color
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 4.dp, horizontal = 12.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = KrailTheme.typography.title,
            color = if (selected) themeContentColor() else themeColor,
        )
    }
}

enum class RadioButtonType {
    SMALL,
    DEFAULT,
}
