package xyz.ksharma.krail.database.sydney.trains.database.api

import xyz.ksharma.krail.sydney.trains.database.StopTimes

interface SydneyTrainsStaticDB {

    suspend fun insertStopTimes()

    suspend fun clearStopTimes()

    suspend fun getStopTimes(): List<StopTimes>
}
