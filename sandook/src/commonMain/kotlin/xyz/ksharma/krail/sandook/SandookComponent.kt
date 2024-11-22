package xyz.ksharma.krail.sandook

import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.KmpComponentCreate
import me.tatarka.inject.annotations.Provides

@Component
abstract class SandookComponent : SQLPlatformComponent {

    internal val RealSandookDb.bind: SandookDb
        @Provides get() = this

    abstract val sandookDb: SandookDb

    companion object
}

@KmpComponentCreate
expect fun createSandookComponent(): SandookComponent
