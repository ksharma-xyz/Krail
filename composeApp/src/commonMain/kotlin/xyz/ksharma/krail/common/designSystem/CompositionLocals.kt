package xyz.ksharma.krail.common.designSystem

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

const val unspecifiedColor = "#FF000000"

val LocalTextStyle = compositionLocalOf { TextStyle.Default }
val LocalTextColor = compositionLocalOf { Color.Unspecified }
val LocalContentAlpha = compositionLocalOf { 1f }
val LocalThemeColor = compositionLocalOf { mutableStateOf(unspecifiedColor) }
val LocalThemeContentColor = compositionLocalOf { mutableStateOf(unspecifiedColor) }

internal val LocalContentColor = compositionLocalOf { Color.Unspecified }
val LocalOnContentColor = compositionLocalOf { Color.Unspecified }
