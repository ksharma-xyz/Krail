package xyz.ksharma.krail.core.analytics.di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.analytics
import org.koin.dsl.module
import xyz.ksharma.krail.core.analytics.Analytics
import xyz.ksharma.krail.core.analytics.RealAnalytics

val analyticsModule = module {
    single<Analytics> {
        RealAnalytics(
            firebaseAnalytics = Firebase.analytics
        )
    }
}
