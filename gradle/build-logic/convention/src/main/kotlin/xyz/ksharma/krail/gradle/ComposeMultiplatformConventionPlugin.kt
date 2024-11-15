package xyz.ksharma.krail.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ComposeMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {

        with(pluginManager) {
            apply("org.jetbrains.kotlin.multiplatform") // Kotlin Multiplatform plugin - JVM, Android, iOS, JS
            apply("org.jetbrains.compose") // Compose Multiplatform plugin - Android Desktop, Web
            apply("org.jetbrains.kotlin.plugin.compose")
        }

        extensions.configure<KotlinMultiplatformExtension> {
            applyDefaultHierarchyTemplate()


            iosArm64()
            iosSimulatorArm64()

            configureJava()
        }
    }
}
