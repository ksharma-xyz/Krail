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
                api(libs.di.koinComposeViewmodel)
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
