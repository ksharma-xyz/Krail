package xyz.ksharma.krail.core.appinfo.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import xyz.ksharma.krail.core.appinfo.AndroidAppInfoProvider
import xyz.ksharma.krail.core.appinfo.AppInfoProvider
import xyz.ksharma.krail.core.di.DispatchersComponent

actual val appInfoModule = module {
    single<AppInfoProvider> {
        AndroidAppInfoProvider(
            context = androidContext(),
            defaultDispatcher = DispatchersComponent().defaultDispatcher,
        )
    }
}
