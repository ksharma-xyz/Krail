package xyz.ksharma.krail.design.system

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

internal val LocalTextStyle = compositionLocalOf { TextStyle.Default }
internal val LocalTextColor = compositionLocalOf { Color.Unspecified }
internal val LocalContentAlpha = compositionLocalOf { 1f }
internal val LocalTransportColor = compositionLocalOf { Color.Unspecified }

internal  val LocalContentColor = compositionLocalOf { Color.Unspecified }
internal  val LocalOnContentColor = compositionLocalOf { Color.Unspecified }
