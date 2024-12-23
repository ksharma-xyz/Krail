package xyz.ksharma.krail.core.appinfo

import platform.Foundation.NSBundle
import platform.UIKit.UIApplication
import platform.UIKit.UIDevice
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle
import kotlin.experimental.ExperimentalNativeApi

class IOSAppInfo : AppInfo {

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

    override val osVersion: String
        get() = UIDevice.currentDevice.systemVersion

    override val fontSize: String
        get() {
            val contentSizeCategory = UIApplication.sharedApplication.preferredContentSizeCategory
            return contentSizeCategory.toString()
        }

    override val isDarkTheme: Boolean
        get() {
            val userInterfaceStyle = UIScreen.mainScreen.traitCollection.userInterfaceStyle
            return userInterfaceStyle == UIUserInterfaceStyle.UIUserInterfaceStyleDark
        }

    override val deviceModel: String
        get() = UIDevice.currentDevice.model

    override val deviceManufacturer: String
        get() = "Apple"

    override fun toString() =
        "AndroidAppInfo(type=$type, isDebug=${isDebug()}, version=$version, osVersion=$osVersion, " +
                "fontSize=$fontSize, isDarkTheme=$isDarkTheme, deviceModel=$deviceModel, " +
                "deviceManufacturer=$deviceManufacturer)"
}


class IosAppInfoProvider : AppInfoProvider {
    override fun getAppInfo(): AppInfo {
        return IOSAppInfo()
    }
}

actual fun getAppPlatformType() = AppPlatformType.IOS
