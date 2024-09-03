package xyz.ksharma.krail.network

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
 * ZipInputStream - The input stream containing the ZIP entry data.
 *
 * @param isDirectory Indicates whether the entry is a directory.
 * @param path The target path in the cache directory.
 */
internal fun ZipInputStream.writeToCache(
    isDirectory: Boolean,
    path: Path,
) {
    if (isDirectory) {
        Files.createDirectories(path)
    } else {
        // Handle creation of parent directories
        if (path.parent != null && Files.notExists(path.parent)) {
            Files.createDirectories(path.parent)
        }
        Files.copy(this, path, StandardCopyOption.REPLACE_EXISTING)
    }
}
