package xyz.ksharma.krail.core.io.di

import org.koin.core.module.Module
import org.koin.dsl.module
import xyz.ksharma.krail.core.io.RealZipFileManager
import xyz.ksharma.krail.core.io.ZipFileManager

expect val fileStorageModule: Module

val ioModule = module {
    single<ZipFileManager> { RealZipFileManager() }
}
