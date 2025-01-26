package xyz.ksharma.krail.core.io

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer
import okio.openZip
import okio.use
import xyz.ksharma.krail.core.appinfo.AppInfoProvider
import xyz.ksharma.krail.core.appinfo.DevicePlatformType
import xyz.ksharma.krail.core.di.DispatchersComponent
import xyz.ksharma.krail.core.log.log

/**
 * A real implementation of [ZipFileManager] that uses Okio to unzip files.
 */
internal class RealZipFileManager(
    private val appInfoProvider: AppInfoProvider,
    private val ioDispatcher: CoroutineDispatcher = DispatchersComponent().ioDispatcher,
) : ZipFileManager {

    override suspend fun unzip(zipPath: Path, destinationPath: Path?) = withContext(ioDispatcher) {
        log("Unpacking Zip: $zipPath")
        var filePrefix: String? = null

        val destDir: Path =
            if (appInfoProvider.getAppInfo().devicePlatformType == DevicePlatformType.IOS) {
                // Note: For iOS, use the zip file name as a prefix to identify the unzipped files.
                // This prevents conflicts when multiple files with the same name are extracted
                // to the same directory from different zip files.
                // E.g. iOS : sydneytrains.zip -> sydneytrains_*.txt
                // E.g. Android : sydneytrains.zip -> sydneytrains/*.txt
//                filePrefix = zipPath.name.dropExtension() + "_"

                destinationPath ?: zipPath.parent?.resolve(zipPath.name.dropExtension())
                ?: throw IllegalArgumentException("Invalid path: $zipPath")
            } else {
                destinationPath ?: zipPath.parent?.resolve(zipPath.name.dropExtension())
                ?: throw IllegalArgumentException("Invalid path: $zipPath")
            }

        log("Zip Unpack Destination: $destDir")

        fileSystem.createDirectories(destDir)

        // region Debugging Code
        checkIfPathExists(destDir)
        val paths = listPathsUnderDirectory(zipPath.parent!!)
        log("Paths under directory: $destDir")
        log("\t" + paths.joinToString("\n"))
        // endregion Debugging Code end

        unpackZipToDirectory(zipPath, destDir, prefix = filePrefix)
    }

    /**
     * Unpacks the contents of a zip file to a specified destination directory.
     *
     * @param zipFile The path to the zip file to be unpacked.
     * @param destDir The path to the destination directory where the contents will be unpacked.
     */
    private suspend fun unpackZipToDirectory(zipFile: Path, destDir: Path, prefix: String? = null) =
        withContext(ioDispatcher) {
            // Open the zip file as a file system
            val zipFileSystem = fileSystem.openZip(zipFile)

            // List all regular files in the zip file recursively
            val paths = zipFileSystem.listRecursively("/".toPath())
                .filter { zipFileSystem.metadata(it).isRegularFile }.toList()
            log("Unzipping ${paths.size} files from $zipFile to $destDir")

            // Iterate over each file path in the zip file
            paths.forEach { zipFilePath ->
                log("Unzipping: $zipFilePath")

                // Open the source file from the zip file system
                zipFileSystem.source(zipFilePath).buffer().use { source ->
                    // Determine the relative file path and resolve it to the destination directory
                    val relativeFilePath = zipFilePath.toString().trimStart('/')
                    val prefixedFilePath = prefix?.let { "$prefix$relativeFilePath" } ?: relativeFilePath
                    val fileToWrite = destDir.resolve(prefixedFilePath)
                    log("Writing to: $fileToWrite")

                    // Create parent directories for the destination file
                    fileToWrite.createParentDirectories()

                    // Write the contents of the source file to the destination file
                    fileSystem.sink(fileToWrite).buffer().use { sink ->
                        val bytes = sink.writeAll(source)
                        log("Unzipped: $relativeFilePath to $fileToWrite; $bytes bytes written")
                    }
                }
            }
        }

    private fun Path.createParentDirectories() = parent?.let { parent ->
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
