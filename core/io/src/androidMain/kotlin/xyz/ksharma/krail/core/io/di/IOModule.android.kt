package xyz.ksharma.krail.core.io.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.ksharma.krail.core.io.AndroidFileStorage
import xyz.ksharma.krail.core.io.FileStorage
import xyz.ksharma.krail.core.io.RealZipFileManager
import xyz.ksharma.krail.core.io.ZipFileManager

actual val ioModule = module {
    single<FileStorage> {
        AndroidFileStorage(context = androidContext())
    }

    singleOf(::RealZipFileManager) { bind<ZipFileManager>() }
}
