plugins {
    alias(libs.plugins.krail.kotlin.multiplatform)
    alias(libs.plugins.krail.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
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
        commonMain  {
            dependencies {
                implementation(projects.taj)
                implementation(projects.feature.tripPlanner.state)
                implementation(projects.core.dateTime)
                implementation(projects.sandook)
                implementation(projects.feature.tripPlanner.network)

                implementation(compose.foundation)
                implementation(compose.animation)
                implementation(compose.ui)
                implementation(compose.material3)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)

                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.collections.immutable)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.navigation.compose)
                implementation(libs.lifecycle.viewmodel.compose)

                implementation(libs.di.kotlinInjectRuntime)

                // TODO - remove once DI added - start
                implementation(libs.multiplatform.settings)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.client.auth)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.kotlinx.datetime)
                implementation(libs.slf4j.simple) // Logging
                // TODO - remove once DI added - end
            }
        }
        commonTest {
            dependencies {
                implementation(libs.test.kotlin)
            }
        }
    }
}

android {
    namespace = "xyz.ksharma.krail.trip.planner.ui"
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


/*
dependencies {
    implementation(projects.core.dateTime)
    implementation(projects.core.designSystem)
    implementation(projects.feature.tripPlanner.network.api)
    implementation(projects.feature.tripPlanner.state)
    implementation(projects.sandook.api)

    implementation(projects.sandook.real)
    implementation(libs.compose.material3)

    testImplementation(libs.test.composeUiTestJunit4)
    testImplementation(libs.test.kotlin)
}
*/
