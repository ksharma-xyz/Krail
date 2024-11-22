package xyz.ksharma.krail.sandook

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

class AndroidDatabaseDriverFactory(private val context: Context) : SandookDriverFactory {

    override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = KrailSandook.Schema,
            context = context,
            name = "krailSandook.db"
        )
    }
}
