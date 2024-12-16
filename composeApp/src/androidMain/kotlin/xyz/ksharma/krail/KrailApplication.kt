package xyz.ksharma.krail

import android.app.Application
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize

class KrailApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        Firebase.initialize(context = this)
    }

    companion object {
        var instance: Application? = null
    }
}
