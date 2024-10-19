plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.krail.android.hilt)
}

android {
    namespace = "xyz.ksharma.krail.sandook.real"
}

dependencies {
    implementation(projects.sandook.api)

    implementation(libs.kotlinx.serialization.json)
}
