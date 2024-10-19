package xyz.ksharma.krail.sandook.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import xyz.ksharma.krail.sandook.RealSandook
import xyz.ksharma.krail.sandook.Sandook
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SandookModule {

    @Binds
    @Singleton
    abstract fun bindSandook(impl: RealSandook): Sandook

    companion object {

        @Provides
        fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
            return context.getSharedPreferences("sandook_key_value", Context.MODE_PRIVATE)
        }
    }
}
