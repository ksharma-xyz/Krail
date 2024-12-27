package xyz.ksharma.krail.sandook.migrations

import app.cash.sqldelight.db.SqlDriver

interface SandookMigration {

    fun migrate(sqlDriver: SqlDriver)
}
