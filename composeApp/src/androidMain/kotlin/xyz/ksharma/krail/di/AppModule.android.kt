package xyz.ksharma.krail.di

import org.koin.android.ext.koin.androidLogger
import org.koin.dsl.koinConfiguration

actual fun nativeConfig() = koinConfiguration {
    androidLogger()
}
