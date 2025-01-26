package xyz.ksharma.krail.core.io.di

import org.koin.dsl.module
import xyz.ksharma.krail.core.io.FileStorage
import xyz.ksharma.krail.core.io.IosFileStorage

actual val ioModule = module {
    single<FileStorage> { IosFileStorage() }
}
