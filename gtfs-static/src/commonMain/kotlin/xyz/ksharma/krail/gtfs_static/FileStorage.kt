package xyz.ksharma.krail.gtfs_static

interface FileStorage {
    suspend fun saveFile(fileName: String, data: ByteArray)
}
