plugins {
    alias(libs.plugins.start.android.library)
    alias(libs.plugins.start.android.hilt)
}

android {
    namespace = "xyz.ksharma.krail.domain"
}

dependencies {
    api(projects.core.data)
    api(projects.core.model)

    testImplementation(libs.junit)
    testImplementation(libs.turbine)
    testImplementation(libs.google.truth)
}
