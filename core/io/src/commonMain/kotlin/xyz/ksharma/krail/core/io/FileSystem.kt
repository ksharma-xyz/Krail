package xyz.ksharma.krail.core.io

import okio.FileSystem

/**
 * okio.FileSystem.SYSTEM is only available on platform specific implementations
 */
expect val fileSystem: FileSystem
