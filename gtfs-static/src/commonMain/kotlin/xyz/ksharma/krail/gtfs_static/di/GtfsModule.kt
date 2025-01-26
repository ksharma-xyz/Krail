package xyz.ksharma.krail.gtfs_static.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module
import xyz.ksharma.krail.gtfs_static.NswGtfsService
import xyz.ksharma.krail.gtfs_static.RealNswGtfsService

val gtfsModule = module {
    single<NswGtfsService> {
        RealNswGtfsService(
            httpClient = get(),
            fileStorage = get(),
            zipManager = get(),
            coroutineScope = CoroutineScope(context = SupervisorJob() + Dispatchers.Default),
        )
    }
}
