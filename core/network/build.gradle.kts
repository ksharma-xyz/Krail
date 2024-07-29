plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.krail.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "xyz.ksharma.krail.network"
}

dependencies {
    api(projects.core.di)

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.converter.gson)
}
