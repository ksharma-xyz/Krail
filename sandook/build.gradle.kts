import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.krail.kotlin.multiplatform)
    alias(libs.plugins.krail.compose.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
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
        commonMain {
            dependencies {
//               implementation(projects.core.di)

                implementation(libs.kotlinx.serialization.json)
                implementation(libs.di.kotlinInjectRuntime)
                implementation(libs.multiplatform.settings)

                implementation(compose.runtime)
                implementation(libs.log.kermit)
            }
        }
    }

    //configureCommonMainKsp()
}

dependencies {
    // 1. Configure code generation into the common source set
    kspCommonMainMetadata(libs.di.kotlinInjectRuntime)
    ksp(libs.di.kotlinInjectCompilerKsp)
    // 2. Configure code generation into each KMP target source set
    kspAndroid(libs.di.kotlinInjectCompilerKsp)
     //kspIosX64("me.tatarka.inject:kotlin-inject-compiler-ksp:0.7.2")
    kspIosArm64(libs.di.kotlinInjectCompilerKsp)
    kspIosSimulatorArm64(libs.di.kotlinInjectCompilerKsp)
}

ksp {
    arg("me.tatarka.inject.generateCompanionExtensions", "true")
}
/*
fun KotlinMultiplatformExtension.configureCommonMainKsp() {
    sourceSets.named("commonMain").configure {
        kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
    }

    project.tasks.withType(KotlinCompilationTask::class.java).configureEach {
        if(name != "kspCommonMainKotlinMetadata") {
            dependsOn("kspCommonMainKotlinMetadata")
        }
    }*/
