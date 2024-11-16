package xyz.ksharma.krail.di

import me.tatarka.inject.annotations.Qualifier

enum class AppDispatchers {
    Default,
    IO,
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatch: AppDispatchers)
