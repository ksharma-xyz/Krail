package xyz.ksharma.krail.sandook

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

class IosSandookDriverFactory : SandookDriverFactory {

    override fun createDriver(): SqlDriver {
        return NativeSqliteDriver(schema = KrailSandook.Schema, name = "krailSandook.db")
    }
}
