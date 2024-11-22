import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.krail.kotlin.multiplatform)
    alias(libs.plugins.krail.compose.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.sqldelight)
}

android {
    namespace = "xyz.ksharma.krail.sandook"
}

kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget()

    iosArm64()
    iosSimulatorArm64()

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_17.majorVersion))
        }
    }

    sourceSets {
        androidMain {
            dependencies {
                implementation(libs.db.sqlAndroidDriver)
                implementation(libs.di.koinAndroid)
            }
        }

        commonMain {
            dependencies {
//               implementation(projects.core.di)

                implementation(libs.kotlinx.serialization.json)
                implementation(libs.di.kotlinInjectRuntime)
                implementation(libs.multiplatform.settings)
                implementation(compose.runtime)
                implementation(libs.log.kermit)
                implementation(libs.kotlinx.datetime)
                implementation(libs.db.sqlRuntime)

                api(libs.di.koinComposeViewmodelNav)
            }
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.db.sqlNativeDriver)
        }
    }
}

dependencies {
    // 1. Configure code generation into the common source set
    kspCommonMainMetadata(libs.di.kotlinInjectRuntime)
    // 2. Configure code generation into each KMP target source set
    kspAndroid(libs.di.kotlinInjectCompilerKsp)
    kspIosArm64(libs.di.kotlinInjectCompilerKsp)
    kspIosSimulatorArm64(libs.di.kotlinInjectCompilerKsp)
}

ksp {
    arg("me.tatarka.inject.generateCompanionExtensions", "true")
}

sqldelight {
    databases {
        create("KrailSandook") {
            packageName.set("xyz.ksharma.krail.sandook")
        }
    }
}
