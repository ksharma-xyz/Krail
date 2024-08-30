plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.krail.android.hilt)
}

android {
    namespace = "xyz.ksharma.krail.domain"
}

dependencies {
    api(projects.core.data)
    api(projects.core.model)
    implementation(projects.core.utils)

    testImplementation(libs.junit)
    testImplementation(libs.turbine)
    testImplementation(libs.google.truth)
}
