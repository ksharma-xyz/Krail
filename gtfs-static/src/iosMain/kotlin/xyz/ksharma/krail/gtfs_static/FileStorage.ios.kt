package xyz.ksharma.krail.gtfs_static

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.crashlytics.crashlytics
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import okio.Path
import okio.Path.Companion.toPath
import platform.Foundation.NSData
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import platform.Foundation.dataWithBytes
import platform.Foundation.writeToFile
import xyz.ksharma.krail.core.log.log

class IosFileStorage : FileStorage {

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun saveFile(fileName: String, data: ByteArray): Path {
        log("Trying to Save file: $fileName")
        return runCatching {
            val fileManager = NSFileManager.defaultManager
            val directory = fileManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = true,
                error = null,
            )?.path

            val filePath = "$directory/$fileName"
            data.usePinned { pinned ->
                NSData.dataWithBytes(pinned.addressOf(0), data.size.toULong())
                    .writeToFile(filePath, true)
            }
            log("File saved: $filePath")
            filePath.toPath()
        }.onFailure {
            log("Failed to save file: $it")
            Firebase.crashlytics.recordException(it) // todo - move to another module
        }.getOrThrow()
    }
}
