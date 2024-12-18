package xyz.ksharma.krail.core.appinfo

import android.content.Context
import android.os.Build
import android.content.res.Configuration

class AndroidAppInfo(private val context: Context) : AppInfo {

    override val devicePlatformType: DevicePlatformType = DevicePlatformType.ANDROID

    override val isDebug: Boolean
        get() =
            context.applicationInfo.flags and android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE != 0

    override val appVersion: String
        get() {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.versionName ?: "Unknown"
        }
    override val osVersion: String
        get() = Build.VERSION.SDK_INT.toString()

    override val fontSize: String
        get() {
            val configuration = context.resources.configuration
            return configuration.fontScale.toString()
        }

    override val isDarkTheme: Boolean
        get() {
            val uiMode = context.resources.configuration.uiMode
            return (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
        }

    override val deviceModel: String
        get() = Build.MODEL

    override val deviceManufacturer: String
        get() = Build.BRAND

    override fun toString() =
        "AndroidAppInfo(type=$devicePlatformType, isDebug=${isDebug}, version=$appVersion, osVersion=$osVersion, " +
                "fontSize=$fontSize, isDarkTheme=$isDarkTheme, deviceModel=$deviceModel, " +
                "deviceManufacturer=$deviceManufacturer)"
}

class AndroidAppInfoProvider(private val context: Context) : AppInfoProvider {
    override fun getAppInfo(): AppInfo {
        return AndroidAppInfo(context)
    }
}

actual fun getAppPlatformType() = DevicePlatformType.ANDROID
