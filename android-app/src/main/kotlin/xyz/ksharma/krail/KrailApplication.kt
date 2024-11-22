package xyz.ksharma.krail

import android.app.Application
import xyz.ksharma.krail.common.AndroidApplicationComponent

class KrailApplication : Application() {

/*
    val component: AndroidApplicationComponent by lazy {
        AndroidApplicationComponent::class.create(this)
    }
*/

    override fun onCreate() {
        super.onCreate()
    }
}
