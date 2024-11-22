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
    alias(libs.plugins.ksp)
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
        androidMain {
            dependencies {
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

                implementation(libs.di.koinAndroid)
            }

        }

        commonMain.dependencies {
            implementation(projects.taj)
            implementation(projects.sandook)
            implementation(projects.feature.tripPlanner.network)
//            implementation(projects.feature.tripPlanner.ui)
//            implementation(projects.feature.tripPlanner.state)

            implementation(libs.navigation.compose)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(libs.multiplatform.settings)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)

            implementation(libs.di.koinComposeViewmodelNav)
            implementation(libs.di.kotlinInjectRuntime)
        }
    }
}

dependencies {
    // 1. Configure code generation into the common source set
    kspCommonMainMetadata(libs.di.kotlinInjectRuntime)
    // 2. Configure code generation into each KMP target source set
    //kspAndroid(libs.di.kotlinInjectCompilerKsp)
    kspIosArm64(libs.di.kotlinInjectCompilerKsp)
    kspIosSimulatorArm64(libs.di.kotlinInjectCompilerKsp)
}

ksp {
    arg("me.tatarka.inject.dumpGraph", "true")
    arg("me.tatarka.inject.generateCompanionExtensions", "true")
}
