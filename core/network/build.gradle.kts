import java.util.Properties

plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.krail.android.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.wire)
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

wire {
    kotlin {
        javaInterop = true
    }
    sourcePath {
        srcDir(files("src/main/proto"))
    }
}

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
