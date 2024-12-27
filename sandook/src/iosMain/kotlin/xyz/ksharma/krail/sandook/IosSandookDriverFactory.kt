package xyz.ksharma.krail.sandook

import app.cash.sqldelight.db.AfterVersion
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import xyz.ksharma.krail.sandook.migrations.SandookMigrationAfter1

class IosSandookDriverFactory : SandookDriverFactory {
    override fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = KrailSandook.Schema,
            name = "krailSandook.db",
            callbacks = getMigrationCallbacks(),
        )
    }

    private fun getMigrationCallbacks(): Array<AfterVersion> = arrayOf(
        AfterVersion(1) { SandookMigrationAfter1.migrate(it) },
    )
}
