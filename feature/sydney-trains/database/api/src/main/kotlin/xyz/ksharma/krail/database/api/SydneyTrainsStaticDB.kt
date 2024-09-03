package xyz.ksharma.krail.database.api

import xyz.ksharma.krail.StopTimes

interface SydneyTrainsStaticDB {

    suspend fun insertStopTimes()

    suspend fun clearStopTimes()

    suspend fun getStopTimes(): List<StopTimes>
}
