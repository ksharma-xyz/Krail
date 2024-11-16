plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.krail.kotlin.multiplatform)
    alias(libs.plugins.ksp)

}

android {
    namespace = "xyz.ksharma.core.di"
}

kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.di.kotlinInjectRuntime)
                implementation(libs.kotlinx.coroutines.core)
            }
        }
    }
}

dependencies {
    // 1. Configure code generation into the common source set
    kspCommonMainMetadata(libs.di.kotlinInjectRuntime)

    // 2. Configure code generation into each KMP target source set
    kspAndroid(libs.di.kotlinInjectCompilerKsp)
   // kspIosX64("me.tatarka.inject:kotlin-inject-compiler-ksp:0.7.2")
   // kspIosArm64("me.tatarka.inject:kotlin-inject-compiler-ksp:0.7.2")
   // kspIosSimulatorArm64("me.tatarka.inject:kotlin-inject-compiler-ksp:0.7.2")
}
