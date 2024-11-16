import java.util.Properties

plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.krail.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

// Get local.properties values
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}
val nswTransportApiKey: String = localProperties.getProperty("NSW_TRANSPORT_API_KEY", "")

android {
    namespace = "xyz.ksharma.krail.network"

    buildTypes {
        debug {
            buildConfigField("String", "NSW_TRANSPORT_API_KEY", "\"$nswTransportApiKey\"")
        }

        release {
            buildConfigField("String", "NSW_TRANSPORT_API_KEY", "\"$nswTransportApiKey\"")
        }
    }
}

/*
dependencies {
    api(projects.core.di)
    implementation(projects.core.coroutinesExt)
    implementation(libs.test.androidxCoreKtx)
    implementation(libs.kotlinx.serialization.json)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
}
*/

kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.coroutinesExt)
                api(projects.core.di)

                implementation(libs.di.kotlinInjectRuntime)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.serialization.kotlinx.json)
                //implementation(libs.kotlinx.coroutines.core)
            }
        }
    }
}

dependencies {
    // 1. Configure code generation into the common source set
    kspCommonMainMetadata(libs.di.kotlinInjectRuntime)

    // 2. Configure code generation into each KMP target source set
    //kspAndroid(libs.di.kotlinInjectCompilerKsp)
    // kspIosX64("me.tatarka.inject:kotlin-inject-compiler-ksp:0.7.2")
    // kspIosArm64("me.tatarka.inject:kotlin-inject-compiler-ksp:0.7.2")
    // kspIosSimulatorArm64("me.tatarka.inject:kotlin-inject-compiler-ksp:0.7.2")
}
