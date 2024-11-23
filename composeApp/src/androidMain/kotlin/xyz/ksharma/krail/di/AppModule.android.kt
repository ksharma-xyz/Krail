package xyz.ksharma.krail.di

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.dsl.koinConfiguration
import xyz.ksharma.krail.KrailApplication

actual fun nativeConfig() = koinConfiguration {
    androidLogger()
    androidContext(KrailApplication.instance ?: error("No Android application context set"))
}
