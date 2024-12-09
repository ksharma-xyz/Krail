package xyz.ksharma.krail.core.appinfo

import androidx.compose.runtime.staticCompositionLocalOf

val LocalAppPlatformProvider = staticCompositionLocalOf { getAppPlatformType() }

expect fun getAppPlatformType(): AppPlatformType
