plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.krail.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "xyz.ksharma.krail.data"
}

dependencies {
    api(projects.core.di)
    implementation(projects.core.coroutinesExt)
    implementation(projects.core.network)
    implementation(projects.core.model)
    implementation(projects.core.utils)

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.opencsv)
    implementation(libs.okhttp)
    implementation(libs.timber)

    testImplementation(libs.turbine)
    testImplementation(libs.google.truth)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
}
