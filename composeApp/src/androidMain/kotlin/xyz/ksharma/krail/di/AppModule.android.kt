package xyz.ksharma.krail.di

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import xyz.ksharma.krail.KrailApplication

actual fun nativeConfig(): KoinApplication.() -> Unit = {
    androidLogger()
    androidContext(KrailApplication.instance ?: error("No Android application context set"))
}
