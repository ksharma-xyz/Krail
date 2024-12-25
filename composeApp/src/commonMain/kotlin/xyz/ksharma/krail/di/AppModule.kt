package xyz.ksharma.krail.di

import org.koin.core.KoinApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import xyz.ksharma.krail.splash.SplashViewModel

val splashModule = module {
    viewModelOf(::SplashViewModel)
}

expect fun nativeConfig(): KoinApplication.() -> Unit
