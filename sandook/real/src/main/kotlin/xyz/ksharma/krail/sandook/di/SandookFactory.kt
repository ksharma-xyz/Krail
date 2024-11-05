package xyz.ksharma.krail.sandook.di

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import xyz.ksharma.krail.sandook.RealSandook
import xyz.ksharma.krail.sandook.Sandook
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SandookFactory @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun create(sandookKey: SandookKey): Sandook {
        val sharedPreferences = context.getSharedPreferences(sandookKey.fileName, Context.MODE_PRIVATE)
        return RealSandook(sharedPreferences)
    }

    enum class SandookKey(val fileName: String) {
        SAVED_TRIP("saved_trip"),
        THEME("theme"),
    }
}
