package xyz.ksharma.krail.core.appinfo

import platform.UIKit.UIDevice
import kotlin.experimental.ExperimentalNativeApi

class IOSAppPlatform : AppPlatform {
    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    override val type: PlatformType = PlatformType.IOS

    @OptIn(ExperimentalNativeApi::class)
    override fun isDebug(): Boolean = Platform.isDebugBinary
}

actual fun getAppPlatform(): AppPlatform = IOSAppPlatform()
