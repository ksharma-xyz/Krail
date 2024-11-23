package xyz.ksharma.krail.sandook.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.ksharma.krail.sandook.RealSandook
import xyz.ksharma.krail.sandook.Sandook

val sandookModule = module {
    singleOf(::RealSandook) { bind<Sandook>() }
    includes(sqlDriverModule)
}

expect val sqlDriverModule: Module
