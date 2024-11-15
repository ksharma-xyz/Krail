package xyz.ksharma.krail.common

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
