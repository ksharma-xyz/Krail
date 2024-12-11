package xyz.ksharma.krail.gtfs_static.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.ksharma.krail.gtfs_static.NswGtfsService
import xyz.ksharma.krail.gtfs_static.RealNswGtfsService

val gtfsModule = module {
    singleOf(::RealNswGtfsService) { bind<NswGtfsService>() }
}

expect val fileStorageModule: Module
