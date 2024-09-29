package xyz.ksharma.krail.design.system

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

internal val LocalTextStyle = compositionLocalOf { TextStyle.Default }
internal val LocalTextColor = compositionLocalOf { Color.Unspecified }
