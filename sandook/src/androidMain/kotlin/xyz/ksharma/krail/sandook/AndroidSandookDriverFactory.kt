package xyz.ksharma.krail.sandook

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

class AndroidSandookDriverFactory(private val context: Context) : SandookDriverFactory {
    override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = KrailSandook.Schema,
            context = context,
            name = "krailSandook.db",
            callback = object : AndroidSqliteDriver.Callback(KrailSandook.Schema) {
                override fun onUpgrade(
                    db: SupportSQLiteDatabase,
                    oldVersion: Int,
                    newVersion: Int,
                ) {
                    println("onUpgrade for Alerts Table: oldVersion: $oldVersion, newVersion: $newVersion")

                    db.execSQL(
                        """
                            CREATE TABLE IF NOT EXISTS ServiceAlertsTable (
                            	id INTEGER PRIMARY KEY AUTOINCREMENT,
                            	journeyId TEXT NOT NULL,
                            	heading TEXT NOT NULL,
                            	message TEXT NOT NULL
                            );
                        """.trimIndent()
                    )
                }
                // Handle other version upgrades as needed
            }
        )
    }
}
