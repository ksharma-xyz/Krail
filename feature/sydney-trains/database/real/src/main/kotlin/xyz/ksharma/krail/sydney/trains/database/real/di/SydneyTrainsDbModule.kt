package xyz.ksharma.krail.sydney.trains.database.real.di

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import xyz.ksharma.krail.database.sydney.trains.database.api.StopTimesStore
import xyz.ksharma.krail.database.sydney.trains.database.api.TripsStore
import xyz.ksharma.krail.database.sydney.trains.database.api.ZipEntryCacheManager
import xyz.ksharma.krail.sydney.trains.database.real.RealStopTimesStore
import xyz.ksharma.krail.sydney.trains.database.real.RealTripsStore
import xyz.ksharma.krail.sydney.trains.database.real.RealZipCacheManager
import xyz.ksharma.krail.sydney_trains.database.api.KrailDB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SydneyTrainsDbModule {

    @Binds
    @Singleton
    abstract fun bindStopTimesStore(impl: RealStopTimesStore): StopTimesStore

    @Binds
    @Singleton
    abstract fun bindTripsStore(impl: RealTripsStore): TripsStore

    @Binds
    @Singleton
    abstract fun bindZipEntryCacheManager(impl: RealZipCacheManager): ZipEntryCacheManager

    companion object {

        @Provides
        @Singleton
        fun provideSydneyTrainsStaticDb(
            @ApplicationContext context: Context,
        ): KrailDB {
            val sydneyTrainsStaticDb by lazy {
                KrailDB(
                    driver = AndroidSqliteDriver(
                        schema = KrailDB.Schema,
                        context = context,
                        name = "sydney_trains_static.db"
                    )
                )
            }
            return sydneyTrainsStaticDb
        }
    }
}
