@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package xyz.ksharma.krail.sandook

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
actual interface SQLPlatformComponent {

    @Provides
    fun provideSQLDriver(application: Application): SqlDriver =
        AndroidSqliteDriver(
            schema = KrailSandook.Schema,
            context = application,
            name = "krailSandook.db",
        )

    companion object
}

//actual fun sandookDriverFactory(): SandookDriverFactory = AndroidSandookDriverFactory(context = context)
