package xyz.ksharma.krail.core.io

import android.content.Context
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.crashlytics.crashlytics
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toOkioPath
import okio.buffer
import xyz.ksharma.krail.core.log.log

class AndroidFileStorage(private val context: Context) : FileStorage {

    private val fileSystem = FileSystem.SYSTEM

    override suspend fun saveFile(fileName: String, data: ByteArray): Path {
        return runCatching {
            log("Try Saving file: $fileName")
            val filePath: Path = context.filesDir.toPath().resolve(fileName).toOkioPath()
            fileSystem.sink(filePath).buffer().use { it.write(data) }
            log("File saved at: $filePath")
            filePath
        }.onFailure {
            log("Failed to save file: $it")
            Firebase.crashlytics.recordException(it)
        }.getOrThrow()
    }
}
