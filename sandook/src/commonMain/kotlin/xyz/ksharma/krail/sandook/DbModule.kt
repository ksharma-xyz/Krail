package xyz.ksharma.krail.sandook

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dbModule = module {
    singleOf(::RealSandook) { bind<Sandook>() }
    singleOf(::RealSandookDb) { bind<SandookDb>() }
}
