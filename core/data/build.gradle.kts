plugins {
    alias(libs.plugins.start.android.library)
    alias(libs.plugins.start.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "xyz.ksharma.krail.data"
}

dependencies {
    api(projects.core.di)

    implementation(libs.retrofit.converter.gson)
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.turbine)
    testImplementation(libs.google.truth)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
}
