package xyz.ksharma.krail.taj.modifier

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import xyz.ksharma.krail.taj.LocalThemeColor
import xyz.ksharma.krail.taj.brighten
import xyz.ksharma.krail.taj.hexToComposeColor
import xyz.ksharma.krail.taj.theme.krailRipple

/**
 * Adds a click listener to the Modifier with a custom ripple effect.
 * This method is used when you need a custom ripple effect with a clickable element.
 *
 * @param role The role of the clickable element.
 * @param enabled Whether the clickable element is enabled.
 * @param onClick The callback to be invoked when the element is clicked.

 * @return The modified Modifier with the click listener and custom ripple effect.
 */
@Composable
fun Modifier.klickable(
    role: Role = Role.Button,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
): Modifier {
    val themeColorHex by LocalThemeColor.current
    val themeColor by remember { mutableStateOf(themeColorHex.hexToComposeColor()) }

    return this.clickable(
        role = role,
        interactionSource = interactionSource,
        enabled = enabled,
        indication = krailRipple(color = themeColor.brighten()),
        onClick = onClick,
    )
}
