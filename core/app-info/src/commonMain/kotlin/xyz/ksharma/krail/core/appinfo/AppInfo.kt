package xyz.ksharma.krail.core.appinfo

/**
 * Inject [AppInfoProvider] to get instance of [AppInfo].
 */
interface AppInfo {
    val type: AppPlatformType

    fun isDebug(): Boolean

    val version: String

    val osVersion: String

    val fontSize: String

    val isDarkTheme: Boolean

    val deviceModel: String

    val deviceManufacturer: String
}

enum class AppPlatformType {
    ANDROID,
    IOS,
    UNKNOWN,
}

interface AppInfoProvider {
    fun getAppInfo(): AppInfo
}
