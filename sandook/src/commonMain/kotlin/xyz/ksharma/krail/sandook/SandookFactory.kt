package xyz.ksharma.krail.sandook

import app.cash.sqldelight.db.SqlDriver
import me.tatarka.inject.annotations.Inject

@Inject
class SandookFactory(private val driver: SqlDriver) {
    fun build(): KrailSandook = KrailSandook(driver = driver)
}

//expect fun sandookDriverFactory(): SandookDriverFactory
