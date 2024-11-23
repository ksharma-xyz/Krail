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
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "network"
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            api(libs.di.koinAndroid)
        }

        commonMain {
            dependencies {

                implementation(libs.kotlinx.serialization.json)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.auth)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.kotlinx.datetime)
                implementation(compose.runtime)
                implementation(libs.slf4j.simple) // Logging

                api(libs.di.koinComposeViewmodel)
            }
        }

        iosMain {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.test.kotlin)
                implementation(libs.test.turbine)
                implementation(libs.test.kotlinxCoroutineTest)
            }
        }
    }
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
