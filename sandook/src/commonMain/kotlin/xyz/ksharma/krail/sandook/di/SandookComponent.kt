package xyz.ksharma.krail.sandook.di

import com.russhwolf.settings.Settings
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import xyz.ksharma.krail.sandook.RealSandook
import xyz.ksharma.krail.sandook.Sandook

@Component
abstract class SandookComponent {
    abstract val sandook: Sandook

    // Settings() does not provide encryption use dependency without no-arg if encryption is required.
    @Provides
    fun provideSettings(): Settings = Settings()

    @Provides
    fun provideSandook(settings: Settings): Sandook = RealSandook(settings)

    companion object
}
