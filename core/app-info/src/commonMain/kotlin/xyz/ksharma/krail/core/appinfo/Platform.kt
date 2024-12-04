package xyz.ksharma.krail.core.appinfo

interface Platform {
    val name: String
    val type: PlatformType
}

enum class PlatformType {
    ANDROID,
    IOS,
    UNKNOWN,
}

expect fun getPlatform(): Platform
