plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.krail.android.library.compose)
    alias(libs.plugins.krail.android.hilt)
    alias(libs.plugins.cash.paparazzi)
}

android {
    namespace = "xyz.ksharma.feature1"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.designSystem)

    api(libs.lifecycle.runtime.compose)
    api(libs.lifecycle.viewmodel.compose)

    testImplementation(libs.junit)
    testImplementation(libs.turbine)
    testImplementation(libs.androidx.test.archcore)
    testImplementation(libs.google.truth)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.paparazzi)
}
