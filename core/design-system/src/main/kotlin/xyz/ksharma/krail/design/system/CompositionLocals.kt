package xyz.ksharma.krail.design.system

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

val LocalTextStyle = compositionLocalOf { TextStyle.Default }
val LocalTextColor = compositionLocalOf { Color.Unspecified }
val LocalContentAlpha = compositionLocalOf { 1f }
val LocalTransportColor = compositionLocalOf { Color.Unspecified }

internal  val LocalContentColor = compositionLocalOf { Color.Unspecified }
val LocalOnContentColor = compositionLocalOf { Color.Unspecified }
