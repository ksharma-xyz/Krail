package xyz.ksharma.krail.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class ComposeMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {

        with(pluginManager) {
            // Compose Multiplatform plugin - Supports building UIs for Android, Desktop, and Web
            apply("org.jetbrains.compose")

            // Enables Compose-specific features in Kotlin projects
            apply("org.jetbrains.kotlin.plugin.compose")
        }
    }
}
