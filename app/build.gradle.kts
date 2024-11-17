plugins {
    alias(libs.plugins.krail.android.application)
    alias(libs.plugins.krail.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "xyz.ksharma.krail"

    defaultConfig {
        applicationId = "xyz.ksharma.krail"
        versionCode = 12
        versionName = "1.0-alpha03"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {

        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            ndk {
                isDebuggable = true
                debugSymbolLevel = "FULL"
            }
        }

        release {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            ndk {
                isDebuggable = false
                debugSymbolLevel = "FULL"
            }
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            firebaseCrashlytics {
                nativeSymbolUploadEnabled = true
            }
        }
    }

    hilt {
        enableAggregatingTask = true
    }
}

dependencies {

    // Projects
    implementation(projects.core.designSystem)
    implementation(projects.core.network)
    implementation(projects.feature.tripPlanner.network.api)
    implementation(projects.feature.tripPlanner.network.real)
    implementation(projects.feature.tripPlanner.state)
    implementation(projects.feature.tripPlanner.ui)
    implementation(projects.sandook.api)
    implementation(projects.sandook.real)

    implementation(libs.activity.compose)
    implementation(libs.compose.foundation)
    implementation(libs.compose.navigation)
    implementation(libs.core.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.timber)
    implementation(libs.hilt.navigation.compose)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.perf)

    // Test
    androidTestImplementation(libs.test.androidxTestExtJunit)
    testImplementation(libs.test.composeUiTestJunit4)
    testImplementation(libs.test.paparazzi)
}
