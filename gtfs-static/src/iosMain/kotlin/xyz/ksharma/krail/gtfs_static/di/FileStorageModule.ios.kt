package xyz.ksharma.krail.gtfs_static.di

import org.koin.dsl.module
import xyz.ksharma.krail.gtfs_static.FileStorage
import xyz.ksharma.krail.gtfs_static.IosFileStorage

actual val fileStorageModule = module {
    single<FileStorage> { IosFileStorage() }
}
