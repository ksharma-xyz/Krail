package xyz.ksharma.krail.core.appinfo

import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    override val type: PlatformType = PlatformType.IOS
}

actual fun getPlatform(): Platform = IOSPlatform()
