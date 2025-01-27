package xyz.ksharma.krail.core.io.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import xyz.ksharma.krail.core.io.AndroidFileStorage
import xyz.ksharma.krail.core.io.FileStorage

actual val fileStorageModule = module {
    single<FileStorage> {
        AndroidFileStorage(context = androidContext())
    }
}
