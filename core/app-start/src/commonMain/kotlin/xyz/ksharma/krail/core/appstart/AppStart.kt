package xyz.ksharma.krail.core.appstart

interface AppStart {

    /**
     * Called when the app starts.
     * Perform all events here that should happen at app launch here.
     *
     * Only those operations should be launched here that should be scoped to application level.
     */
    fun start()
}
