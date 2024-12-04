package xyz.ksharma.krail.core.appinfo

import androidx.compose.runtime.staticCompositionLocalOf

val LocalPlatformTypeProvider = staticCompositionLocalOf { getAppPlatform() }
