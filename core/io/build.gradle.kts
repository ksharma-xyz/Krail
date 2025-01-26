android {
    namespace = "xyz.ksharma.krail.core.io"
}

plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.krail.kotlin.multiplatform)
    alias(libs.plugins.krail.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {
            api(libs.di.koinAndroid)
        }

        commonMain {
            dependencies {
                implementation(projects.core.di)
                implementation(projects.core.log)

                implementation(libs.kotlinx.serialization.json)
                implementation(compose.runtime)
                api(libs.di.koinComposeViewmodel)
                implementation(libs.firebase.gitLiveCrashlytics)

                implementation(libs.kotlinx.ioCore)
                implementation(libs.okio)
            }
        }

        iosMain {
            dependencies {}
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
