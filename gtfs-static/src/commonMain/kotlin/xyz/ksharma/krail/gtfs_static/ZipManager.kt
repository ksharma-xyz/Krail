package xyz.ksharma.krail.gtfs_static

import okio.Path
import okio.Path.Companion.toPath
import okio.buffer
import okio.openZip
import okio.use
import xyz.ksharma.krail.core.log.log

fun readZip(zipPath: String) {
    println("Reading Zip: $zipPath")

    // Use the same directory as the zip file for unpacking
    val zipFilePath = zipPath.toPath()
    val parentDir = zipFilePath.parent ?: throw IllegalArgumentException("Invalid path: $zipPath")

    unpackZip(zipFilePath, parentDir)
}

fun unpackZip(zipFile: Path, destDir: Path) {
    val zipFileSystem = fileSystem.openZip(zipFile)
    val paths = zipFileSystem.listRecursively("/".toPath())
        .filter { zipFileSystem.metadata(it).isRegularFile }
        .toList()
    log("Unzipping ${paths.size} files from $zipFile to $destDir")

    paths.forEach { zipFilePath ->
        println("Unzipping: $zipFilePath")
        zipFileSystem.source(zipFilePath).buffer().use { source ->
            val relativeFilePath = zipFilePath.toString().trimStart('/')
            val fileToWrite = destDir.resolve(relativeFilePath)
            println("Writing to: $fileToWrite")
            fileToWrite.createParentDirectories()
            fileSystem.sink(fileToWrite).buffer().use { sink ->
                val bytes = sink.writeAll(source)
                println("Unzipped: $relativeFilePath to $fileToWrite; $bytes bytes written")
            }
        }
    }
}

fun Path.createParentDirectories() {
    this.parent?.let { parent ->
        fileSystem.createDirectories(parent)
    }
}
