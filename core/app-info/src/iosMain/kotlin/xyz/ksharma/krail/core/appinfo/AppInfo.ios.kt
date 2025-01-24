package xyz.ksharma.krail.core.appinfo

import platform.Foundation.NSBundle
import platform.UIKit.UIApplication
import platform.UIKit.UIDevice
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle
import kotlin.experimental.ExperimentalNativeApi

class IOSAppInfo : AppInfo {

    override val devicePlatformType: DevicePlatformType = DevicePlatformType.IOS

    @OptIn(ExperimentalNativeApi::class)
    override val isDebug: Boolean
        get() = Platform.isDebugBinary

    override val appVersion: String
        get() {
            val shortVersion =
                NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString") as? String
                    ?: "Unknown"
            val buildVersion =
                NSBundle.mainBundle.infoDictionary?.get("CFBundleVersion") as? String ?: "Unknown"
            return "$shortVersion ${if (isDebug) "($buildVersion)" else ""}"
        }

    override val osVersion: String
        get() = UIDevice.currentDevice.systemVersion

    override val fontSize: String
        get() {
            val contentSizeCategory = UIApplication.sharedApplication.preferredContentSizeCategory
            return contentSizeCategory?.substring(startIndex = "UICTContentSizeCategory".length)
                ?: contentSizeCategory.toString()
        }

    override val isDarkTheme: Boolean
        get() {
            val userInterfaceStyle = UIScreen.mainScreen.traitCollection.userInterfaceStyle
            return userInterfaceStyle == UIUserInterfaceStyle.UIUserInterfaceStyleDark
        }

    /**
     * TODO - what a hack! This is not the device model.
     *    There needs to be a better way to do it without using 3rd party libraries.
     */
    override val deviceModel: String
        get() = UIDevice.currentDevice.model

    override val deviceManufacturer: String
        get() = "Apple"

    override fun toString() =
        "AndroidAppInfo(type=$devicePlatformType, isDebug=${isDebug}, version=$appVersion, osVersion=$osVersion, " +
                "fontSize=$fontSize, isDarkTheme=$isDarkTheme, deviceModel=$deviceModel, " +
                "deviceManufacturer=$deviceManufacturer)"
}

class IosAppInfoProvider : AppInfoProvider {
    override fun getAppInfo(): AppInfo {
        return IOSAppInfo()
    }
}

actual fun getAppPlatformType() = DevicePlatformType.IOS
