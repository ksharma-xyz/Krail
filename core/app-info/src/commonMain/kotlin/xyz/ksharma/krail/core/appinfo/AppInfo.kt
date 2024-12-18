package xyz.ksharma.krail.core.appinfo

/**
 * Inject [AppInfoProvider] to get instance of [AppInfo].
 */
interface AppInfo {
    val devicePlatformType: DevicePlatformType

    val isDebug: Boolean

    /**
     * App version.
     */
    val appVersion: String

    val osVersion: String

    val fontSize: String

    val isDarkTheme: Boolean

    val deviceModel: String

    val deviceManufacturer: String
}

enum class DevicePlatformType {
    ANDROID,
    IOS,
    UNKNOWN,
}

interface AppInfoProvider {
    fun getAppInfo(): AppInfo
}
