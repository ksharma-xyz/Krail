plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.krail.android.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.wire)
}

android {
    namespace = "xyz.ksharma.krail.network"
}

wire {
    kotlin {
        javaInterop = true
    }
    sourcePath {
        srcDir("src/main/proto")
    }
}

dependencies {
    api(projects.core.di)

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
}
