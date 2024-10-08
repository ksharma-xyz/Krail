plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.krail.android.hilt)
}

android {
    namespace = "xyz.ksharma.krail.domain"
}

dependencies {
    api(projects.core.model)
    implementation(projects.core.utils)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.turbine)
    testImplementation(libs.test.googleTruth)
}
