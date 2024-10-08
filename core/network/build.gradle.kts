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
        srcDir(files("src/main/proto"))
    }
}

dependencies {
    api(projects.core.di)
    implementation(projects.core.coroutinesExt)
    implementation(projects.core.utils)

    implementation(libs.test.androidxCoreKtx)
    implementation(libs.kotlinx.serialization.json)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
}
