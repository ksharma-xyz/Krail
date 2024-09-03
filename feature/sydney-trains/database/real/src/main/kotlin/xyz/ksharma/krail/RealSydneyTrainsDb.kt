package xyz.ksharma.krail

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.ksharma.krail.database.api.SydneyTrainsDB
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealSydneyTrainsDb @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    coroutineScope: CoroutineScope,
    @ApplicationContext private val applicationContext: Context,
) : SydneyTrainsDB {

    init {
        coroutineScope.launch { createDb() }
    }

    override suspend fun createDb(): Unit = withContext(ioDispatcher) {
        val db = KrailDB(
            driver = AndroidSqliteDriver(
                schema = KrailDB.Schema,
                context = applicationContext,
                name = "krail_db.db"
            )
        )
    }

    override suspend fun insertStopTimes() {
        TODO("Not yet implemented")
    }

    override suspend fun clearStopTimes() {
        TODO("Not yet implemented")
    }
}
