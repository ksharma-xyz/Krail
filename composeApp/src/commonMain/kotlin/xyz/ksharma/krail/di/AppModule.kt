package xyz.ksharma.krail.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes
import org.koin.dsl.koinConfiguration
import org.koin.dsl.module
import xyz.ksharma.krail.sandook.di.sandookModule
import xyz.ksharma.krail.splash.SplashViewModel
import xyz.ksharma.krail.trip.planner.network.api.di.networkModule
import xyz.ksharma.krail.trip.planner.ui.di.viewModelsModule
import xyz.ksharma.krail.version.appVersionModule

val koinConfig = koinConfiguration {
    includes(nativeConfig())
    modules(
        networkModule,
        viewModelsModule,
        sandookModule,
        splashModule,
        appVersionModule,
    )
}

val splashModule = module {
    viewModelOf(::SplashViewModel)
}

expect fun nativeConfig(): KoinAppDeclaration
