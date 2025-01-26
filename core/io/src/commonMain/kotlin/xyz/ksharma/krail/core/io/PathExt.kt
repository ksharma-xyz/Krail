package xyz.ksharma.krail.core.io

import okio.Path
import xyz.ksharma.krail.core.log.log

internal fun checkIfPathExists(destDir: Path) {
    if (fileSystem.exists(destDir)) {
        log("Path Exists: $destDir")
    } else {
        log("Path does not exist: $destDir")
    }
}

internal fun listPathsUnderDirectory(directory: Path): List<Path> {
    return runCatching {
        fileSystem.list(directory)
    }.getOrElse { error ->
        log("Failed to list paths under directory: $directory. Error: ${error.message}")
        emptyList()
    }
}

internal fun String.dropExtension(): String {
    val lastDot = this.lastIndexOf('.')
    return if (lastDot > 0) this.substring(0, lastDot) else this
}
