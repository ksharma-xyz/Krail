package xyz.ksharma.krail.common

import android.app.Application
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
abstract class AndroidApplicationComponent(
    @get:Provides val application: Application,
) {
    protected val RealX.bind: X
        @Provides get() = this

    companion object
}

class RealX() : X{
    override fun a() {
        println("RealX")
    }

}

interface  X {
    fun a()
}
