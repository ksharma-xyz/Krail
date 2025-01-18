package xyz.ksharma.krail.trip.planner.ui.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import krail.feature.trip_planner.ui.generated.resources.Res
import krail.feature.trip_planner.ui.generated.resources.ic_paint
import krail.feature.trip_planner.ui.generated.resources.ic_dev
import org.jetbrains.compose.resources.painterResource
import xyz.ksharma.krail.taj.LocalThemeColor
import xyz.ksharma.krail.taj.components.Divider
import xyz.ksharma.krail.taj.components.Text
import xyz.ksharma.krail.taj.components.TitleBar
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.trip.planner.ui.components.hexToComposeColor
import xyz.ksharma.krail.trip.planner.ui.timetable.ActionButton

@Composable
fun SettingsScreen(
    appVersion: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onChangeThemeClick: () -> Unit = {},
) {
    val themeColor by LocalThemeColor.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = KrailTheme.colors.surface)
            .statusBarsPadding(),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            TitleBar(
                modifier = Modifier.fillMaxWidth(),
                onNavActionClick = onBackClick,
                title = { Text(text = "Settings") },
            )
        }

        LazyColumn(
            modifier = Modifier,
            contentPadding = PaddingValues(top = 20.dp, bottom = 104.dp),
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clickable(
                            role = Role.Button,
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = { onChangeThemeClick() },
                        )
                        .padding(vertical = 24.dp)
                        .semantics(mergeDescendants = true) {},
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        contentDescription = null,
                        painter = painterResource(Res.drawable.ic_paint),
                        colorFilter = ColorFilter.tint(color = themeColor.hexToComposeColor()),
                        modifier = Modifier.size(20.dp),
                    )
                    Text(
                        text = "Change Theme",
                        style = KrailTheme.typography.bodyLarge,
                    )
                }
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
            }

            item {
                // todo - common component
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .semantics(mergeDescendants = true) {}
                        .padding(vertical = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        contentDescription = null,
                        painter = painterResource(Res.drawable.ic_dev),
                        colorFilter = ColorFilter.tint(color = themeColor.hexToComposeColor()),
                        modifier = Modifier.size(24.dp),
                    )
                    Text(
                        text = "KRAIL App Version: $appVersion",
                        style = KrailTheme.typography.bodyLarge,
                    )
                }
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}
