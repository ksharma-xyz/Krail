package xyz.ksharma.krail.data

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.zip.ZipInputStream

/**
 * Extracts a ZIP entry to a specified cache path.
 *
 * If the entry is a directory, it creates the directory structure.
 * If the entry is a file, it copies the file contents to the cache path.
 *
 * **Note:** If the target file already exists, it will be overwritten.
 *
 * @param isDirectory Indicates whether the entry is a directory.
 * @param path The target path in the cache directory.
 * @param inputStream The input stream containing the ZIP entry data.
 */
internal fun writeToCacheFromZip(
    isDirectory: Boolean,
    path: Path,
    inputStream: ZipInputStream
) {
    if (isDirectory) {
        Files.createDirectories(path)
    } else {
        // Handle creation of parent directories
        if (path.parent != null && Files.notExists(path.parent)) {
            Files.createDirectories(path.parent)
        }
        Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING)
    }
}
