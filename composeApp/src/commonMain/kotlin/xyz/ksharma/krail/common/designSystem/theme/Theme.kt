package xyz.ksharma.krail.common.designSystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun KrailTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val krailColors = when {
        darkTheme -> KrailDarkColors
        else -> KrailLightColors
    }

    CompositionLocalProvider(
        LocalKrailColors provides krailColors,
        LocalKrailTypography provides krailTypography,
        content = content,
    )
}

object KrailTheme {

    val colors: KrailColors
        @Composable
        get() = LocalKrailColors.current

    val typography: KrailTypography
        @Composable
        get() = LocalKrailTypography.current
}
