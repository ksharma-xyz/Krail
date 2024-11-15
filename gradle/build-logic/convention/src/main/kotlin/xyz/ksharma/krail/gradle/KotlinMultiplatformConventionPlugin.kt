package xyz.ksharma.krail.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {

        with(pluginManager) {
            apply("org.jetbrains.kotlin.multiplatform") // Kotlin Multiplatform plugin - JVM, Android, iOS, JS
        }

        extensions.configure<KotlinMultiplatformExtension> {
            applyDefaultHierarchyTemplate()

            iosArm64()
            iosSimulatorArm64()

            configureJava()
        }
    }
}
