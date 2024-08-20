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
    implementation(projects.core.network)
    implementation(projects.core.model)

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation("com.opencsv:opencsv:5.5.2")

    testImplementation(libs.turbine)
    testImplementation(libs.google.truth)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
}
