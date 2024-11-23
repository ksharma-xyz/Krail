package xyz.ksharma.krail

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
