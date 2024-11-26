package xyz.ksharma.krail.version

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import platform.Foundation.NSBundle

class IOSAppVersionProvider : AppVersionProvider {
    override fun getAppVersion(): String {
        val shortVersion =
            NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString") as? String
                ?: "Unknown"
        val buildVersion =
            NSBundle.mainBundle.infoDictionary?.get("CFBundleVersion") as? String ?: "Unknown"
        return "$shortVersion ($buildVersion)"
    }
}

actual val appVersionModule = module {
    singleOf(::IOSAppVersionProvider) { bind<AppVersionProvider>() }
    single<IOSAppVersionProvider> { IOSAppVersionProvider() }
}
