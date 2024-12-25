package xyz.ksharma.krail.di

import org.koin.core.KoinApplication

actual fun nativeConfig(): KoinApplication.() -> Unit = {
    printLogger()
}
