import java.util.Properties

android {
    namespace = "xyz.ksharma.krail.trip.planner.network"

    buildTypes {
        debug {
            buildConfigField("String", "NSW_TRANSPORT_API_KEY", "\"$nswTransportApiKey\"")
        }

        release {
            buildConfigField("String", "NSW_TRANSPORT_API_KEY", "\"$nswTransportApiKey\"")
        }
    }
}

plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.krail.kotlin.multiplatform)
    alias(libs.plugins.krail.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
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


kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        commonMain {
            dependencies {
                implementation(projects.core.coroutinesExt)
                api(projects.core.di)

                implementation(libs.di.kotlinInjectRuntime)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.kotlinx.datetime)

                implementation(compose.runtime)
            }
        }

        iosMain {
            dependencies {
                implementation(libs.ktor.client.darwin)
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
