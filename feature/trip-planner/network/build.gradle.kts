import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec

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
    alias(libs.plugins.buildkonfig)
}

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
                implementation(libs.di.kotlinInjectRuntime)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.client.auth)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.kotlinx.datetime)
                implementation(compose.runtime)
                implementation(libs.slf4j.simple) // Logging
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

// READ API KEY
val localProperties = gradleLocalProperties(rootProject.rootDir, providers)
val nswTransportApiKey: String = localProperties.getProperty("NSW_TRANSPORT_API_KEY", "")
require(nswTransportApiKey.isNotEmpty()) {
    "Register your API key from the developer and place it in local.properties as `API_KEY`"
}
buildkonfig {
    packageName = "xyz.ksharma.krail.trip.planner.network"

    require(nswTransportApiKey.isNotEmpty()) {
        "Register your api key from developer and place it in local.properties as `API_KEY`"
    }

    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "NSW_TRANSPORT_API_KEY", nswTransportApiKey)
    }
}
