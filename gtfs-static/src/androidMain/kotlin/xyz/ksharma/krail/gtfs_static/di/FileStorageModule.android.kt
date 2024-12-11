package xyz.ksharma.krail.gtfs_static.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import xyz.ksharma.krail.gtfs_static.AndroidFileStorage
import xyz.ksharma.krail.gtfs_static.FileStorage

actual val fileStorageModule = module {
    single<FileStorage> {
        AndroidFileStorage(context = androidContext())
    }
}
