package xyz.ksharma.krail.core.appinfo.di

import org.koin.dsl.module
import xyz.ksharma.krail.core.appinfo.AppInfoProvider
import xyz.ksharma.krail.core.appinfo.IosAppInfoProvider

actual val appInfoModule = module {
    single<AppInfoProvider> {
        IosAppInfoProvider()
    }
}
