package xyz.ksharma.core.test.fakes

import xyz.ksharma.krail.gtfs_static.NswGtfsService

class FakeGtfsService : NswGtfsService {

    override suspend fun getSydneyTrains() {
        // Do nothing
    }

    override suspend fun getSydneyMetro() {
        // Do nothing
    }

    override suspend fun getLightRail() {
        // Do nothing
    }

    override suspend fun getNswTrains() {
        // Do nothing
    }

    override suspend fun getSydneyFerries() {
        // Do nothing
    }
}
