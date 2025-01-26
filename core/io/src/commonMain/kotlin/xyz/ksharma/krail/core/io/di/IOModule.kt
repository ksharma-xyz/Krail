package xyz.ksharma.krail.core.io.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.ksharma.krail.core.io.RealZipFileManager
import xyz.ksharma.krail.core.io.ZipFileManager

expect val fileStorageModule: Module

val ioModule = module {
    singleOf(::RealZipFileManager) { bind<ZipFileManager>() }
}
