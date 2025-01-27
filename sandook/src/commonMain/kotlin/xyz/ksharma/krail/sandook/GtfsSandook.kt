package xyz.ksharma.krail.sandook

interface GtfsSandook {

}

internal class RealGtfsSandook(factory: SandookDriverFactory) : GtfsSandook {

    private val query = SydneyStopsQueries(factory.createDriver())

    fun test () {
        query.selectStopsByPartialName("Central").executeAsList()
    }
}
