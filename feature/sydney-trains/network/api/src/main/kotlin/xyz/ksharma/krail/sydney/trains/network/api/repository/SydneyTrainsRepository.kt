package xyz.ksharma.krail.sydney.trains.network.api.repository

interface SydneyTrainsRepository {

    suspend fun fetchStaticSydneyTrainsScheduleAndCache()
}
