package xyz.ksharma.krail.core.appinfo

interface AppPlatform {
    val name: String
    val type: PlatformType

    fun isDebug(): Boolean
}

enum class PlatformType {
    ANDROID,
    IOS,
    UNKNOWN,
}

expect fun getAppPlatform(): AppPlatform
