package xyz.ksharma.krail

import android.app.Application

class KrailApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        var instance : Application? = null
    }
}
