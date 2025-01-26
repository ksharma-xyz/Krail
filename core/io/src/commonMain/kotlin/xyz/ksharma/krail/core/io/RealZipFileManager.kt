package xyz.ksharma.krail.core.io

import okio.Path
import okio.Path.Companion.toPath
import okio.buffer
import okio.openZip
import okio.use
import xyz.ksharma.krail.core.log.log

internal class RealZipFileManager : ZipFileManager {

    override fun unZip(zipPath: Path, destinationPath: Path?) {
        log("Unpacking Zip: $zipPath")

        // For ios use zipPath.parent only as dir creation is not allowed
        val destDir: Path = destinationPath ?: zipPath.parent?.resolve(zipPath.name.dropExtension())
        ?: throw IllegalArgumentException("Invalid path: $zipPath")
        log("Zip Unpack Destination: $destDir")

        fileSystem.createDirectories(destDir)

        unpackZipToDirectory(zipPath, destDir)
    }

    private fun unpackZipToDirectory(zipFile: Path, destDir: Path) {
        val zipFileSystem = fileSystem.openZip(zipFile)
        val paths = zipFileSystem.listRecursively("/".toPath())
            .filter { zipFileSystem.metadata(it).isRegularFile }.toList()
        log("Unzipping ${paths.size} files from $zipFile to $destDir")

        paths.forEach { zipFilePath ->
            log("Unzipping: $zipFilePath")
            zipFileSystem.source(zipFilePath).buffer().use { source ->
                val relativeFilePath = zipFilePath.toString().trimStart('/')
                val fileToWrite = destDir.resolve(relativeFilePath)
                log("Writing to: $fileToWrite")
                fileToWrite.createParentDirectories()
                fileSystem.sink(fileToWrite).buffer().use { sink ->
                    val bytes = sink.writeAll(source)
                    log("Unzipped: $relativeFilePath to $fileToWrite; $bytes bytes written")
                }
            }
        }
    }

    private fun Path.createParentDirectories() {
        this.parent?.let { parent ->
            try {
                log("Creating directories: $parent")
                fileSystem.createDirectories(parent)
                log("Directories created successfully: $parent")
            } catch (e: Exception) {
                log("Failed to create directories: $parent. Error: ${e.message}")
                throw e
            }
        }
    }
}

// TODO - write UTs
private fun String.dropExtension(): String {
    val lastDot = this.lastIndexOf('.')
    return if (lastDot > 0) this.substring(0, lastDot) else this
}
