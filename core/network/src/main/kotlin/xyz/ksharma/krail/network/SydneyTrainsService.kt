package xyz.ksharma.krail.network

interface SydneyTrainsService {
    suspend fun fetchSydneyTrains(): ByteArray
}
