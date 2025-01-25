package xyz.ksharma.krail.gtfs_static

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer
import okio.openZip
import okio.use

expect val fileSystem: FileSystem
