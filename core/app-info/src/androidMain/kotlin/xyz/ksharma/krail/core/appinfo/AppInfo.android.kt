package xyz.ksharma.krail.core.appinfo

import android.content.Context
import android.os.Build

class AndroidAppInfo(private val context: Context) : AppInfo {

    override val name: String = "Android ${Build.VERSION.SDK_INT}"

    override val type: AppPlatformType = AppPlatformType.ANDROID

    override fun isDebug(): Boolean =
        context.applicationInfo.flags and android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE != 0

    override val version: String
        get() {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.versionName ?: "Unknown"
        }
}

class AndroidAppInfoProvider(private val context: Context) : AppInfoProvider {
    override fun getAppInfo(): AppInfo {
        return AndroidAppInfo(context)
    }
}

actual fun getAppPlatformType() = AppPlatformType.ANDROID
