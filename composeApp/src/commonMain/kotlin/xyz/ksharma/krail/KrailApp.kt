package xyz.ksharma.krail

import androidx.compose.runtime.Composable
import org.koin.compose.KoinApplication
import org.koin.dsl.includes
import xyz.ksharma.krail.core.analytics.di.analyticsModule
import xyz.ksharma.krail.core.appinfo.di.appInfoModule
import xyz.ksharma.krail.di.nativeConfig
import xyz.ksharma.krail.di.splashModule
import xyz.ksharma.krail.sandook.di.sandookModule
import xyz.ksharma.krail.taj.theme.KrailTheme
import xyz.ksharma.krail.trip.planner.network.api.di.networkModule
import xyz.ksharma.krail.trip.planner.ui.di.alertsCacheModule
import xyz.ksharma.krail.trip.planner.ui.di.viewModelsModule

@Composable
fun KrailApp() {
    KoinApplication(
        application = {
            includes(nativeConfig())
            modules(
                networkModule,
                viewModelsModule,
                sandookModule,
                splashModule,
                appInfoModule,
                alertsCacheModule,
                analyticsModule,
            )
        },
    ) {
        KrailTheme {
            KrailNavHost()
        }
    }
}
