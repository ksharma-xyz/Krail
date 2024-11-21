package xyz.ksharma.krail.sandook.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.ksharma.krail.sandook.RealSandook
import xyz.ksharma.krail.sandook.Sandook

val dbModule = module {
    singleOf(::RealSandook) { bind<Sandook>() }
}
