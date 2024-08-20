package xyz.ksharma.krail.network

interface GtfsService {

    suspend fun getSydneyTrainSchedule(): ByteArray
}
