package xyz.ksharma.krail.database.api

interface SydneyTrainsDB {

    suspend fun createDb()

    suspend fun insertStopTimes()

    suspend fun clearStopTimes()
}
