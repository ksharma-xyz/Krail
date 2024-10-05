plugins {
    alias(libs.plugins.krail.android.library.compose)
    alias(libs.plugins.krail.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "xyz.ksharma.krail.trip_planner.ui"
}

dependencies {
    implementation(projects.core.designSystem)

    implementation(libs.compose.foundation)
    implementation(libs.compose.navigation)
    implementation(libs.kotlinx.serialization.json)
}
