package xyz.ksharma.krail.sandook

import app.cash.sqldelight.db.SqlDriver

interface SandookDriverFactory {
    fun createDriver(): SqlDriver
}
