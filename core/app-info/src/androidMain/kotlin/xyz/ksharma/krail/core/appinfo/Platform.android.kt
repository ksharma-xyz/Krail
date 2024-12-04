package xyz.ksharma.krail.core.appinfo

import android.os.Build

class AndroidAppPlatform : AppPlatform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val type: PlatformType = PlatformType.ANDROID
    override fun isDebug(): Boolean = BuildConfig.DEBUG
}

actual fun getAppPlatform(): AppPlatform = AndroidAppPlatform()
