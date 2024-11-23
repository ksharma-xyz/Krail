package xyz.ksharma.krail.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinAndroidConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {

        with(pluginManager) {
            apply("org.jetbrains.kotlin.android") // Support Kotlin in Android projects
        }
        configureJava()
    }
}
