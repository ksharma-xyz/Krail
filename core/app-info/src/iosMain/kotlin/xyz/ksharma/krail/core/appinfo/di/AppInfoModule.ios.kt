package xyz.ksharma.krail.core.appinfo.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.ksharma.krail.core.appinfo.AppInfoProvider
import xyz.ksharma.krail.core.appinfo.IosAppInfoProvider

actual val appInfoModule = module {
    singleOf(::IosAppInfoProvider) { bind<AppInfoProvider>() }
    single<IosAppInfoProvider> {
        IosAppInfoProvider()
    }
}
