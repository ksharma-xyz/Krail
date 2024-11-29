package xyz.ksharma.krail.trip.planner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
                color = if (selected) themeColor else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = if (!selected) 1.dp else 0.dp, // Apply border only when not selected
                color = themeColor, // Use backgroundColor for border color
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 4.dp, horizontal = 12.dp),
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
