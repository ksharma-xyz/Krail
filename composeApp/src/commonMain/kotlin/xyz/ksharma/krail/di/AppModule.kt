package xyz.ksharma.krail.di

import org.koin.core.KoinApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.includes
import org.koin.dsl.koinConfiguration
import org.koin.dsl.module
import xyz.ksharma.krail.splash.SplashViewModel
import xyz.ksharma.krail.core.analytics.di.analyticsModule
import xyz.ksharma.krail.core.appinfo.di.appInfoModule
import xyz.ksharma.krail.core.remote_config.di.remoteConfigModule
import xyz.ksharma.krail.sandook.di.sandookModule
import xyz.ksharma.krail.trip.planner.network.api.di.networkModule
import xyz.ksharma.krail.trip.planner.ui.di.viewModelsModule

val koinConfig = koinConfiguration {
    includes(nativeConfig())
    modules(
        networkModule,
        viewModelsModule,
        sandookModule,
        splashModule,
        appInfoModule,
        analyticsModule,
        remoteConfigModule,
    )
}

val splashModule = module {
    viewModelOf(::SplashViewModel)
}

expect fun nativeConfig(): KoinApplication.() -> Unit
