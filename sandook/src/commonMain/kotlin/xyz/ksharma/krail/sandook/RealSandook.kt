package xyz.ksharma.krail.sandook

import com.russhwolf.settings.Settings

class RealSandook : Sandook {
    private val settings: Settings = Settings()

    override fun keys(): Set<String> = settings.keys

    override fun putString(key: String, value: String) {
        settings.putString(key, value)
    }

    override fun getString(key: String, defaultValue: String?): String? {
        return settings.getStringOrNull(key) ?: defaultValue
    }

    override fun putInt(key: String, value: Int) {
        settings.putInt(key, value)
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return settings.getInt(key, defaultValue)
    }

    override fun putBoolean(key: String, value: Boolean) {
        settings.putBoolean(key, value)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return settings.getBoolean(key, defaultValue)
    }

    override fun putFloat(key: String, value: Float) {
        settings.putFloat(key, value)
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return settings.getFloat(key, defaultValue)
    }

    override fun putLong(key: String, value: Long) {
        settings.putLong(key, value)
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return settings.getLong(key, defaultValue)
    }

    override fun remove(key: String) {
        settings.remove(key)
    }

    override fun clear() {
        settings.clear()
    }
}
