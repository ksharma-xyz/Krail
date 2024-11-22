package xyz.ksharma.krail.common.di

import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.includes
import org.koin.dsl.koinConfiguration
import org.koin.dsl.module
import xyz.ksharma.krail.sandook.dbModule
import xyz.ksharma.krail.trip.planner.network.api.di.networkModule
import xyz.ksharma.krail.trip.planner.ui.di.viewModelsModule
import xyz.ksharma.krail.common.splash.SplashViewModel

val koinConfig = koinConfiguration {
    modules(networkModule + dbModule + viewModelsModule + splashModule)
}

val splashModule = module {
    viewModelOf(::SplashViewModel)
}
