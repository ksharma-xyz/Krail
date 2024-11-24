package xyz.ksharma.krail.trip.planner.ui.alerts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun HtmlText(text: String, modifier: Modifier = Modifier, onClick: () -> Unit)
