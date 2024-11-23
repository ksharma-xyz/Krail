package xyz.ksharma.krail.sandook.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.ksharma.krail.sandook.AndroidSandookDriverFactory
import xyz.ksharma.krail.sandook.RealSandook
import xyz.ksharma.krail.sandook.SandookDriverFactory

actual val sqlDriverModule = module {
    singleOf(::AndroidSandookDriverFactory) { bind<SandookDriverFactory>() }

    single<RealSandook> {
        RealSandook(
            factory = AndroidSandookDriverFactory(
                context = androidContext()
            )
        )
    }
}
