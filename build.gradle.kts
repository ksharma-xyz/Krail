import io.gitlab.arturbosch.detekt.Detekt

// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlyticsPlugin) apply false
}

subprojects {
    plugins.withId("app.cash.paparazzi") {
        // Defer until afterEvaluate so that testImplementation is created by Android plugin.
        afterEvaluate {
            dependencies.constraints {
                add("testImplementation", "com.google.guava:guava") {
                    attributes {
                        attribute(
                            TargetJvmEnvironment.TARGET_JVM_ENVIRONMENT_ATTRIBUTE,
                            objects.named(
                                TargetJvmEnvironment::class.java,
                                TargetJvmEnvironment.STANDARD_JVM
                            )
                        )
                    }
                    because(
                        "LayoutLib and sdk-common depend on Guava's -jre published variant." +
                                "See https://github.com/cashapp/paparazzi/issues/906."
                    )
                }
            }
        }
    }
}
true // Needed to make the Suppress annotation work for the plugins block

val excludeFilesDetekt = listOf(
    "**/.gradle/**",
    "**/.idea/**",
    "**/build/**",
    ".github/**",
    "gradle/**",
)

tasks {
    withType<Detekt> {
        config.setFrom(files("$rootDir/config/detekt/detekt.yaml"))
        baseline.set(file("$rootDir/config/detekt/detekt-baseline.xml"))

        autoCorrect = true
        buildUponDefaultConfig = true
        parallel = true
        debug = true

        source = files(subprojects.flatMap { subproject ->
            listOf(
                File(subproject.projectDir, "/src/main/kotlin"),
                File(subproject.projectDir, "/src/main/java"),
                File(subproject.projectDir, "/src/debug/kotlin"),
                File(subproject.projectDir, "/src/debug/java"),
            )
        }).asFileTree

        excludes.addAll(excludeFilesDetekt)

        reports {
            html.required = true
            html.outputLocation.set(file("build/reports/detekt.html"))
            txt.required = true
            md.required = true
            xml.required = false
            sarif.required = false
        }
    }
    project.detekt {
        basePath = rootProject.projectDir.absolutePath
        toolVersion = libs.versions.detekt.get()
        autoCorrect = true
    }
}

dependencies {
    detektPlugins(libs.detektFormat)
    detektPlugins(libs.detektCompose)
}
