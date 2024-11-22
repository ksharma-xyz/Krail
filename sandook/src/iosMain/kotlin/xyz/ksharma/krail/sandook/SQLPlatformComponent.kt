@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package xyz.ksharma.krail.sandook

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
actual interface SQLPlatformComponent {

    @Provides
    fun provideSQLDriver(): SqlDriver =
        NativeSqliteDriver(schema = KrailSandook.Schema, name = "krailSandook.db")

    companion object
}
