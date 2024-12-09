package xyz.ksharma.krail.core.appinfo

import platform.Foundation.NSBundle
import platform.UIKit.UIDevice
import kotlin.experimental.ExperimentalNativeApi

class IOSAppInfo : AppInfo {

    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    override val type: AppPlatformType = AppPlatformType.IOS

    @OptIn(ExperimentalNativeApi::class)
    override fun isDebug(): Boolean = Platform.isDebugBinary

    override val version: String
        get() {
            val shortVersion =
                NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString") as? String
                    ?: "Unknown"
            val buildVersion =
                NSBundle.mainBundle.infoDictionary?.get("CFBundleVersion") as? String ?: "Unknown"
            return "$shortVersion ($buildVersion)"
        }
}


class IosAppInfoProvider : AppInfoProvider {
    override fun getAppInfo(): AppInfo {
        return IOSAppInfo()
    }
}

actual fun getAppPlatformType() = AppPlatformType.IOS
