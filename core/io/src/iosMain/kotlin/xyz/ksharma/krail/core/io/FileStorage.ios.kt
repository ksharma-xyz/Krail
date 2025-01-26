package xyz.ksharma.krail.core.io

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.crashlytics.crashlytics
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import okio.FileSystem.Companion.SYSTEM_TEMPORARY_DIRECTORY
import okio.Path
import okio.Path.Companion.toPath
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes
import platform.Foundation.writeToFile
import xyz.ksharma.krail.core.log.log

class IosFileStorage : FileStorage {

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun saveFile(fileName: String, data: ByteArray): Path {
        log("Trying to Save file: $fileName")
        return runCatching {
            val directory = SYSTEM_TEMPORARY_DIRECTORY
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
