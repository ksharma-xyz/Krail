import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

android {
    namespace = "xyz.ksharma.krail.common"
}

plugins {
    alias(libs.plugins.krail.kotlin.multiplatform)
    alias(libs.plugins.krail.compose.multiplatform)
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    applyDefaultHierarchyTemplate()
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "KrailApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.activity.compose)

            // Projects
            /*
                        implementation(projects.core.network)
                        implementation(projects.core.utils)
                        implementation(projects.feature.tripPlanner.network.api)
                        implementation(projects.feature.tripPlanner.network.real)
                        implementation(projects.feature.tripPlanner.state)
                        implementation(projects.feature.tripPlanner.ui)
                        implementation(projects.sandook.api)
                        implementation(projects.sandook.real)
            */
            implementation(compose.foundation)
            implementation(libs.core.ktx)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.lifecycle.runtime.ktx)
        }

        commonMain.dependencies {
            implementation(projects.taj)
            implementation(projects.sandook)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(libs.multiplatform.settings)
        }
    }
}
