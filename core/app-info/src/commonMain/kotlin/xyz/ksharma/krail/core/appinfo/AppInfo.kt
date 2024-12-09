package xyz.ksharma.krail.core.appinfo

interface AppInfo {
    val name: String

    val type: AppPlatformType

    fun isDebug(): Boolean

    val version: String
}

enum class AppPlatformType {
    ANDROID,
    IOS,
    UNKNOWN,
}

interface AppInfoProvider {
    fun getAppInfo(): AppInfo
}
