@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package xyz.ksharma.krail.sandook

import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

expect interface SQLPlatformComponent

@Component
interface SQLComponent : SQLPlatformComponent {

    //    @ApplicationScope
    @Provides
    fun provideSqlDelightDatabase(
        factory: SandookFactory,
    ): KrailSandook = factory.build()

    companion object
}
