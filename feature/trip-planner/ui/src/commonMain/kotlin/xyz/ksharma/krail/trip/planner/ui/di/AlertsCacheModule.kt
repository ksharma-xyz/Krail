package xyz.ksharma.krail.trip.planner.ui.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.ksharma.krail.trip.planner.ui.alerts.cache.ServiceAlertsCache
import xyz.ksharma.krail.trip.planner.ui.alerts.cache.RealServiceAlertsCache

val alertsCacheModule = module {
    singleOf(::RealServiceAlertsCache) { bind<ServiceAlertsCache>() }
}
