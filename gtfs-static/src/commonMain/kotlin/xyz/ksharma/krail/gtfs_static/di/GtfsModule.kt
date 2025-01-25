package xyz.ksharma.krail.gtfs_static.di

import org.koin.core.module.Module
import org.koin.dsl.module
import xyz.ksharma.krail.gtfs_static.NswGtfsService
import xyz.ksharma.krail.gtfs_static.RealNswGtfsService

val gtfsModule = module {
    single<NswGtfsService> {
        RealNswGtfsService(get(), get())
    }
}

expect val fileStorageModule: Module
