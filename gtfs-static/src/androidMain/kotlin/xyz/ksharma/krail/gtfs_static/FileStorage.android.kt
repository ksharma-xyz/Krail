package xyz.ksharma.krail.gtfs_static

import android.content.Context
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.crashlytics.crashlytics
import xyz.ksharma.krail.core.log.log
import java.io.File

class AndroidFileStorage(private val context: Context) : FileStorage {
    override suspend fun saveFile(fileName: String, data: ByteArray) {
        runCatching {
            val file = File(context.filesDir, fileName)
            file.writeBytes(data)
        }.onFailure {
            log("Failed to save file: $it")
            Firebase.crashlytics.recordException(it)
        }.onSuccess {
            log("File saved at: ${context.filesDir.absolutePath}")
        }
    }
}
