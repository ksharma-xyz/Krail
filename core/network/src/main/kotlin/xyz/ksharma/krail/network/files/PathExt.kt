package xyz.ksharma.krail.network.files

import android.content.Context
import java.nio.file.Path

/**
 * Writes a file to the cache directory.
 *
 * @return The file contents as a [ByteArray].
 */
fun Context.toPath(fileName: String): Path = cacheDir.toPath().resolve(fileName).normalize()
