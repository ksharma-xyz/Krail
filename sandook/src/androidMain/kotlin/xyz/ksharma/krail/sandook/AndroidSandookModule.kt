package xyz.ksharma.krail.sandook

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val androidDbModule = module {
    singleOf(::AndroidSandookDriverFactory) { bind<SandookDriverFactory>() }

    single<RealSandookDb> {
          RealSandookDb(
              factory = AndroidSandookDriverFactory(
                  context = androidContext()
              )
          )
      }
}
