package xyz.ksharma.krail.network.files

import android.content.Context
import java.nio.file.Path

/**
 * Reads a file from the cache directory.
 *
 * @return The file contents as a [ByteArray].
 */
internal fun Path.readFile(): ByteArray = this.toFile().readBytes()

/**
 * Writes a file to the cache directory.
 *
 * @return The file contents as a [ByteArray].
 */
internal fun Context.toPath(fileName: String): Path = cacheDir.toPath().resolve(fileName).normalize()
