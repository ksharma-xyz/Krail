package xyz.ksharma.krail.core.io

import okio.Path

interface FileStorage {

    /**
     * Save a file with the given name and data
     *
     * @param fileName the name of the file to save
     * @param data the data to save
     *
     * @return the [okio.Path] where the file was saved.
     */
    suspend fun saveFile(fileName: String, data: ByteArray) : Path
}
