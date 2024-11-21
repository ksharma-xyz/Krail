package xyz.ksharma.krail.common.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.koinConfiguration
import org.koin.dsl.module
import xyz.ksharma.krail.sandook.di.dbModule
import xyz.ksharma.krail.trip.planner.network.api.di.networkModule
import xyz.ksharma.krail.trip.planner.ui.di.viewModelsModule
import xyz.ksharma.krail.common.splash.SplashViewModel

val koinConfig = koinConfiguration {
    modules(networkModule + dbModule + viewModelsModule + splashModule)
}

val splashModule = module {
    viewModelOf(::SplashViewModel)
}
