package xyz.ksharma.krail.taj.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.taj.LocalContentColor
import xyz.ksharma.krail.taj.LocalTextColor
import xyz.ksharma.krail.taj.LocalTextStyle
import xyz.ksharma.krail.taj.theme.KrailTheme

@Composable
fun TitleBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onNavActionClick: (() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .padding(end = 16.dp, start = 8.dp)
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        onNavActionClick?.let {
            NavActionButton(onClick = onNavActionClick)
        }

        Row(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp),
        ) {
            CompositionLocalProvider(
                LocalTextColor provides KrailTheme.colors.onSurface,
                LocalTextStyle provides KrailTheme.typography.headlineMedium,
            ) {
                title()
            }
        }
        actions?.let {
            Row(
                modifier = Modifier.padding(start = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides KrailTheme.colors.onSurface,
                ) {
                    actions()
                }
            }
        }
    }
}

@Composable
private fun NavActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.size(40.dp).clip(CircleShape)
            .clickable(
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .semantics(mergeDescendants = true) {
                this.contentDescription = "Back"
            },
        contentAlignment = Alignment.Center,
    ) {
        Image(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            colorFilter = ColorFilter.tint(KrailTheme.colors.onSurface),
            modifier = Modifier.size(24.dp),
        )
    }
}
