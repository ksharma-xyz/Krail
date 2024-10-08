package xyz.ksharma.krail.trip_planner.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.preview.ComponentPreviews
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.design.system.R as DSR

@Composable
fun SavedTripCard(
    origin: String,
    destination: String,
    modifier: Modifier = Modifier,
    onStarClick: () -> Unit = {},
    onCardClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = KrailTheme.colors.secondaryContainer)
            .clickable(
                role = Role.Button,
                onClickLabel = "Open Trip Details",
                onClick = onCardClick
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        TransportModeIcon(
            letter = 'T',
            backgroundColor = "#00B5EF".hexToComposeColor(),
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f)
        ) {
            Text(text = origin, style = KrailTheme.typography.bodyMedium)
            Text(text = destination, style = KrailTheme.typography.bodyMedium)
        }

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .clickable(
                    onClickLabel = "Remove Saved Trip",
                    role = Role.RadioButton,
                    onClick = onStarClick,
                )
                .semantics(mergeDescendants = true) {},
            contentAlignment = Alignment.Center,
        ) {
            Image(
                imageVector = ImageVector.vectorResource(DSR.drawable.star),
                contentDescription = "Save Trip",
                colorFilter = ColorFilter.tint(KrailTheme.colors.secondary),
            )
        }
    }
}

// region Previews

@ComponentPreviews
@Composable
private fun SavedTripCardPreview() {
    KrailTheme {
        SavedTripCard(
            origin = "Edmondson Park Station",
            destination = "Harris Park Station",
        )
    }
}


@Preview
@Composable
private fun SavedTripCardListPreview() {
    KrailTheme {

        Column(
            modifier = Modifier
                .background(color = KrailTheme.colors.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            SavedTripCard(
                origin = "Edmondson Park Station",
                destination = "Harris Park Station",
            )

            SavedTripCard(
                origin = "Harrington Street, Stand D",
                destination = "Albert Rd, Stand A",
            )

            SavedTripCard(
                origin = "Manly Wharf",
                destination = "Circular Quay Wharf",
            )
        }
    }
}

// endregion
