package xyz.ksharma.krail.version

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class AndroidAppVersionProvider(private val context: Context) : AppVersionProvider {
    override fun getAppVersion(): String {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        return packageInfo.versionName ?: "Unknown"
    }
}

actual val appVersionModule = module {
    singleOf(::AndroidAppVersionProvider) { bind<AppVersionProvider>() }
    single<AndroidAppVersionProvider> {
        AndroidAppVersionProvider(context = androidContext())
    }
}
