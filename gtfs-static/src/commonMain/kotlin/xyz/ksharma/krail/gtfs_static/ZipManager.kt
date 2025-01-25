package xyz.ksharma.krail.gtfs_static

import okio.Path
import okio.Path.Companion.toPath
import okio.buffer
import okio.openZip
import okio.use

fun readZip(zipPath: String) {
    println("Reading Zip: $zipPath")

    // Ensure the destination directory exists
    val destDir = "unzipped".toPath()
    fileSystem.createDirectories(destDir)

    unpackZip(zipPath.toPath(), "unzipped".toPath())
}

fun unpackZip(zipFile: Path, destDir: Path) {
    val zipFileSystem = fileSystem.openZip(zipFile)
    val paths = zipFileSystem.listRecursively("/".toPath())
        .filter { zipFileSystem.metadata(it).isRegularFile }
        .toList()

    paths.forEach { zipFilePath ->
        zipFileSystem.source(zipFilePath).buffer().use { source ->
            val relativeFilePath = zipFilePath.toString().trimStart('/')
            val fileToWrite = destDir.resolve(relativeFilePath)
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
