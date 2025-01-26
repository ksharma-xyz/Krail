package xyz.ksharma.krail.core.io

import okio.Path
import okio.Path.Companion.toPath
import okio.buffer
import okio.openZip
import okio.use
import xyz.ksharma.krail.core.log.log

interface ZipFileManager {

    /**
     * Reads the contents of a zip file and unpacks it into a directory.
     *
     * @param zipPath the path to the zip file
     *
     * @param destinationPath the path to the directory where the zip file should be unpacked.
     * If this is null, the zip will be unpacked into zipFileDirectory/Zip_file_name directory.
     *
     */
    suspend fun unzip(zipPath: Path, destinationPath: Path? = null)

}
