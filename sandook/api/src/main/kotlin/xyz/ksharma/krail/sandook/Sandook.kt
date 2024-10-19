package xyz.ksharma.krail.sandook

import kotlin.reflect.KClass

interface Sandook {

    fun clear()
    fun remove(key: String)
    fun getLong(key: String, defaultValue: Long = 0L): Long
    fun putLong(key: String, value: Long)
    fun getFloat(key: String, defaultValue: Float = 0f): Float
    fun putFloat(key: String, value: Float)
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean
    fun putBoolean(key: String, value: Boolean)
    fun getInt(key: String, defaultValue: Int = 0): Int
    fun putInt(key: String, value: Int)
    fun getString(key: String, defaultValue: String? = null): String?
    fun putString(key: String, value: String)
}
